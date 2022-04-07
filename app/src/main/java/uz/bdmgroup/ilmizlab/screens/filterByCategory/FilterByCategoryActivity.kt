package uz.bdmgroup.ilmizlab.screens.filterByCategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_filter_by_category.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.*
import uz.bdmgroup.ilmizlab.screens.search_category.regions.RegionActivity
import uz.bdmgroup.ilmizlab.utils.Constants
import uz.bdmgroup.ilmizlab.utils.PrefUtils
import uz.bdmgroup.ilmizlab.view.*
import java.io.Serializable

class FilterByCategoryActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var item:CategoryModel
    var filter=LCFilterModel(0,0,0, 0, "", "",
        0, 0.0, 0.0, false, )
    var selectDistrict:DistrictSearchModel?=null

    var districtResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.data!=null && it.resultCode== RESULT_OK && it.data!!.hasExtra(Constants.EXTRA_DATA)){
            selectDistrict=it.data!!.getSerializableExtra(Constants.EXTRA_DATA) as DistrictSearchModel
            titleVil.text=selectDistrict!!.name_uz
            filter.district_id=selectDistrict!!.id?:0
            loadData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_by_category)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        viewModel.topEduCenterData.observe(this, Observer {
            sortRecycler.layoutManager=GridLayoutManager(this, 2)
            sortRecycler.adapter=EduCenterAdapter(it)
        })

        cardViewSort.setOnClickListener {
            showConfirmationDialog()
        }

        backHome.setOnClickListener {
            finish()
        }

        cardViewVil.setOnClickListener {
           val intent=Intent(this, RegionActivity::class.java)
            if (selectDistrict!=null){
                intent.putExtra(Constants.EXTRA_DATA, selectDistrict as Serializable)
            }
            districtResultLauncher.launch(intent)
        }

        item=intent.getSerializableExtra("go_category") as CategoryModel
        titleActivity.text=item.title
        recylerSortFanHomeScreen.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recylerSortFanHomeScreen.adapter=CategoryDetailAdapter(item.sciences, object:CategoryDetailAdapterCallback{
            override fun onClickItem(item: SciencesModel) {
                filter.science_id=item.id
                loadData()
            }
        })

        filter.category_id=item.id
       loadData()
}
    //---------ALTER DIALOG-------------------------------------------------------------------------
    var selectedItemIndex=0
    fun showConfirmationDialog() {
        val sortNames= arrayOf( "Eng yaqinlari", "Eng saralari")
        var selectedSort=sortNames[selectedItemIndex]

        MaterialAlertDialogBuilder(this)
            .setTitle("Saralash")
            .setSingleChoiceItems(sortNames, selectedItemIndex){ dialog, which ->
                selectedItemIndex=which
                selectedSort=sortNames[which]
            }
            .setPositiveButton("OK"){dialog, which ->
                when(selectedItemIndex){
                    0 ->{
                        titleSort.text="Eng yaqinlari"
                        filter.sort="distance"
                        filter.category_id=item.id
                        filter.latitude=PrefUtils.getLocation()?.lat?:0.0
                        filter.longitude=PrefUtils.getLocation()?.long?:0.0
                       loadData()
                    }
                    1->{
                        titleSort.text="Eng saralari"
                        filter.sort="rating"
                        filter.category_id=item.id
                            loadData()
                    }
                }
            }
            .setNeutralButton("Cancel"){dialog, which->

            }
            .show()
    }
    //----------------------------------------------------------------------------------------------
    fun loadData(){
        viewModel.getEduCenter(filter)
    }
}