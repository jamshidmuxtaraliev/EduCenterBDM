package uz.bdmgroup.ilmizlab.screens.search_category.regions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_fan_search.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.model.SciencesModel
import uz.bdmgroup.ilmizlab.utils.Constants
import uz.bdmgroup.ilmizlab.view.FanAdapter

class FanSearchActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fan_search)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        var selectSciences:SciencesModel?=null

        if (intent.hasExtra(Constants.EXTRA_DATA_FAN)){
            selectSciences=intent.getSerializableExtra(Constants.EXTRA_DATA_FAN) as SciencesModel
        }

        backFan.setOnClickListener{
            finish()
        }

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.categoriesData.observe(this, Observer {
            it.forEach {
                it.sciences.forEach {
                    if (it.id==selectSciences?.id){
                        it.checked=true
                    }
                }
            }
            recyclerFan.adapter= FanAdapter(it)
            recyclerFan.layoutManager= LinearLayoutManager(this)
        })

        //----------------------------------------------------------------------------------
        btnSelectFan.setOnClickListener {
            viewModel.categoriesData.value?.forEach {
                it.sciences.forEach {
                    if (it.checked){
                        selectSciences=it
                    }
                }
            }
            val intent=Intent()
            intent.putExtra(Constants.EXTRA_DATA_FAN, selectSciences)
            setResult(RESULT_OK, intent)
            finish()
        }
        //-------------------------------------------------------------------------------------
        loadData()
    }
    fun loadData(){
        viewModel.getCategories()
    }
}