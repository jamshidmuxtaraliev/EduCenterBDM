package uz.bdmgroup.ilmizlab.screens.search_category.regions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_region.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.DistrictSearchModel
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.utils.Constants
import uz.bdmgroup.ilmizlab.view.RegionSelectAdapter

class RegionActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        var selectDistrict:DistrictSearchModel?=null

        if (intent.hasExtra(Constants.EXTRA_DATA)){
            selectDistrict=intent.getSerializableExtra(Constants.EXTRA_DATA) as DistrictSearchModel
        }

        swipeRegions.setOnRefreshListener {
            loadData()
        }

        backSearch.setOnClickListener{
            finish()
        }

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            swipeRegions.isRefreshing=it
        })

        viewModel.regionData.observe(this, Observer {
            it.forEach {
                it.districts.forEach {
                    if (it.id==selectDistrict?.id){
                    it.checked=true
                } }
            }
            recyclerRegions.adapter=RegionSelectAdapter(it)
            recyclerRegions.layoutManager=LinearLayoutManager(this)
        })

        btnSelectRegion.setOnClickListener {
            viewModel.regionData.value?.forEach {
                it.districts.forEach {
                    if(it.checked){
                        selectDistrict=it
                    }
                }
            }
            val intent=Intent()
            intent.putExtra(Constants.EXTRA_DATA, selectDistrict)
            setResult(RESULT_OK, intent)
            finish()
        }
        loadData()
    }
    fun loadData(){
        viewModel.getRegions()
    }
}