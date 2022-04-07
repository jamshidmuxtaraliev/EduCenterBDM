package uz.bdmgroup.ilmizlab.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_news.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.LCFilterModel
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.utils.Constants
import uz.bdmgroup.ilmizlab.utils.PrefUtils
import uz.bdmgroup.ilmizlab.view.CategoryAdapter
import uz.bdmgroup.ilmizlab.view.EduCenterAdapter
import uz.bdmgroup.ilmizlab.view.TopMarkazAdapter


class HomeFragment : Fragment() {
    var offerList: List<SlideModel> = listOf()
    lateinit var viewModel: MainViewModel
    private lateinit var filter : LCFilterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh.setOnRefreshListener {
            loadData()
        }


        recyclerAtrofdagiMarkazlar.layoutManager= GridLayoutManager(requireActivity(), 2)


        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing=it
            swipeRefresh.visibility=if (it) View.GONE else View.VISIBLE
            flProgressHome.visibility=if (it) View.VISIBLE else View.GONE
        })
        viewModel.categoriesData.observe(viewLifecycleOwner, Observer {
            categoryRecycler.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            categoryRecycler.adapter=CategoryAdapter(it)
        })

        viewModel.topEduCenterData.observe(viewLifecycleOwner, Observer {
            topMarkazRecycler.layoutManager=LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            topMarkazRecycler.adapter=TopMarkazAdapter(it)
        })
        viewModel.nearEduCenterData.observe(viewLifecycleOwner, Observer {
            recyclerAtrofdagiMarkazlar.adapter=EduCenterAdapter(it)
        })

        viewModel.offerData.observe(viewLifecycleOwner, Observer {
            offerList = it.map { SlideModel(Constants.HOSTING_IMAGE + it.image, ScaleTypes.CENTER_CROP) }
            imageSlider.setImageList(offerList)
        })

        loadData()

    }
     fun loadData(){
         viewModel.getCategories()

         filter = LCFilterModel(0,0,0, 0, "", "rating",
             0, 0.0, 0.0, false )
         viewModel.getEduCenter(filter)

         viewModel.getNearEduCenter()

         viewModel.getOffers()
     }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
