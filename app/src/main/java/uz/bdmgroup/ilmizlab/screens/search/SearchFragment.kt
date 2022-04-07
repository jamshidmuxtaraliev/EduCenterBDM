package uz.bdmgroup.ilmizlab.screens.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_search.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.DistrictSearchModel
import uz.bdmgroup.ilmizlab.model.LCFilterModel
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.model.SciencesModel
import uz.bdmgroup.ilmizlab.screens.search_category.regions.FanSearchActivity
import uz.bdmgroup.ilmizlab.screens.search_category.regions.RegionActivity
import uz.bdmgroup.ilmizlab.utils.Constants
import uz.bdmgroup.ilmizlab.utils.PrefUtils
import uz.bdmgroup.ilmizlab.view.EduCenterAdapter
import java.io.Serializable

class SearchFragment : Fragment(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    lateinit var viewModel: MainViewModel
    lateinit var filter:LCFilterModel
    var selectDistrict:DistrictSearchModel?=null
    var selectSciences:SciencesModel?=null

    var districtResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.data!=null && it.resultCode==RESULT_OK && it.data!!.hasExtra(Constants.EXTRA_DATA)){
            selectDistrict=it.data!!.getSerializableExtra(Constants.EXTRA_DATA) as DistrictSearchModel
            titleVil.text=selectDistrict!!.name_uz
            filter.district_id= selectDistrict!!.id ?:0
            loadData()
        }
    }

    var sciencesResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.data!=null && it.resultCode== RESULT_OK && it.data!!.hasExtra(Constants.EXTRA_DATA_FAN)){
            selectSciences=it.data!!.getSerializableExtra(Constants.EXTRA_DATA_FAN) as SciencesModel
            titleFan.text= selectSciences!!.title
            filter.science_id= selectSciences!!.id?:0
            loadData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)
        filter= LCFilterModel( 0,0,0,0,"","rating",
            40,PrefUtils.getLocation()?.lat ?: 0.0,PrefUtils.getLocation()?.long?:0.0,false)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeSearchFragment.setOnRefreshListener {
            loadData()
        }

        cardviewViloyat.setOnClickListener {
            val intent=Intent(requireActivity(), RegionActivity::class.java)
            if (selectDistrict!=null){
                intent.putExtra(Constants.EXTRA_DATA , selectDistrict as Serializable)
            }
            districtResultLauncher.launch(intent)
        }


        cardviewFan.setOnClickListener {
            val intent=Intent(requireActivity(), FanSearchActivity::class.java)
            if (selectSciences!=null){
                intent.putExtra(Constants.EXTRA_DATA_FAN, selectSciences as Serializable)
            }
            sciencesResultLauncher.launch(intent)
        }

        cardviewSortirovka.setOnClickListener {
            showConfirmationDialog(view)
        }


        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            swipeSearchFragment.isRefreshing=it
            swipeSearchFragment.visibility=if (it) View.GONE else View.VISIBLE
            flProgressSerch.visibility=if (it) View.VISIBLE else View.GONE
            recyclerSearch.visibility=if (it) View.GONE else View.VISIBLE
        })

        viewModel.topEduCenterData.observe(viewLifecycleOwner, Observer {
            recyclerSearch.layoutManager=GridLayoutManager(requireActivity(), 2)
            recyclerSearch.adapter=EduCenterAdapter(it)
        })

        searchView.setOnQueryTextListener(this)
        loadData()

    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val items = viewModel.topEduCenterData.value?.filter { it.name.uppercase().contains(newText!!.uppercase()) }
        recyclerSearch.adapter = EduCenterAdapter(items ?: emptyList())
        return false
    }
//--------------------------------------------------------------------------------------------------
    var selectedItemIndex=0
    fun showConfirmationDialog(view: View){
        val sortTypes= arrayOf(  "Eng zo'rlari", "Eng yaqinlari")
        var selectedSort=sortTypes[selectedItemIndex]

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Saralash")
            .setSingleChoiceItems(sortTypes, selectedItemIndex){dialog, which ->
                selectedItemIndex=which
                selectedSort=sortTypes[which]
            }
            .setPositiveButton("OK"){dialog, which ->
                when(selectedItemIndex){
                   0 ->{
                       titleSort.text="Eng saralari"
                       filter.sort="rating"
                       viewModel.getEduCenter(filter)
                   }
                    1->{
                        titleSort.text="Eng yaqinlari"
                        filter.sort="distance"
                        viewModel.getEduCenter(filter)
                    }
                }
            }
            .setNeutralButton("Cancel"){dialog, which->

            }
            .show()
    }
//--------------------------------------------------------------------------------------------------
fun loadData(){
    viewModel.getEduCenter(filter)
    viewModel.getRegions()
    viewModel.getCategories()
}
}
