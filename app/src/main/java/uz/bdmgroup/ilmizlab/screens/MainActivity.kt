package uz.bdmgroup.ilmizlab.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_header_layout.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.LocationModel
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.screens.addCenter.MyCentersActivity
import uz.bdmgroup.ilmizlab.screens.home.HomeFragment
import uz.bdmgroup.ilmizlab.screens.maps.MapFragment
import uz.bdmgroup.ilmizlab.screens.news.NewsFragment
import uz.bdmgroup.ilmizlab.screens.profileActivity.ProfilActivity
import uz.bdmgroup.ilmizlab.screens.registration.SignInActivity
import uz.bdmgroup.ilmizlab.screens.search.SearchFragment
import uz.bdmgroup.ilmizlab.screens.subcribe.SubcribeFragment
import uz.bdmgroup.ilmizlab.utils.PrefUtils

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var homeFragment = HomeFragment.newInstance()
    var searchFragment = SearchFragment.newInstance()
    var mapsFragment = MapFragment()
    var newsFragment = NewsFragment.newInstance()
    var signalFragment = SubcribeFragment.newInstance()
    var activeFragment: Fragment = homeFragment
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        myLocation.setOnClickListener {
            getLocation()
            Toast.makeText(this, "Joylashuv aniqlandi", Toast.LENGTH_SHORT).show()
        }
        getLocation()

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.userData.observe(this, Observer {
            PrefUtils.setUser(it)
            nameProfile.text=it.fullname
            numberProfile.text="+"+it.phone
            Glide.with(profileIMage).load("http://demo-ilm-izlab.devapp.uz/${it.avatar}").into(profileIMage)
        })



        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, signalFragment, signalFragment.tag).hide(signalFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, newsFragment, newsFragment.tag).hide(newsFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, mapsFragment, mapsFragment.tag).hide(mapsFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, searchFragment, searchFragment.tag).hide(searchFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        buttonNavigationMenu.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.homeAction) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(homeFragment).commit()
                activeFragment = homeFragment
            } else if (it.itemId == R.id.mapsAction) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(mapsFragment).commit()
                activeFragment = mapsFragment
            } else if (it.itemId == R.id.newsAction) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(newsFragment).commit()
                activeFragment = newsFragment
            } else if (it.itemId == R.id.searchAction) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(searchFragment).commit()
                activeFragment = searchFragment
            } else if (it.itemId == R.id.signalAction) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(signalFragment).commit()
                activeFragment = signalFragment
            }
            return@setOnNavigationItemSelectedListener true
        }

        if(PrefUtils.getToken().isNullOrEmpty()){
            registerLayout.visibility=View.GONE
            noRegisterLayout.visibility=View.VISIBLE
            profilLayout.visibility=View.GONE
            eduCenterLayout.visibility=View.GONE
            logOutLayout.visibility=View.GONE
        }else{
            registerLayout.visibility=View.VISIBLE
            noRegisterLayout.visibility=View.GONE
            profilLayout.visibility=View.VISIBLE
            eduCenterLayout.visibility=View.VISIBLE
            logOutLayout.visibility=View.VISIBLE
        }

        infoApp.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        logOutLayout.setOnClickListener{
            MaterialAlertDialogBuilder(this)
                .setTitle("Chiqish")
                .setMessage("Tizimdan chiqishga ishonchingiz komilmi")
                .setNegativeButton("Yo'q"){dialog ,which ->
                    Toast.makeText(this,"Tizimida qolganingiz uchun rahmat", Toast.LENGTH_LONG).show()
                }
                .setPositiveButton("Ha Chiqaman"){dialog, which->
                    PrefUtils.deleteToken()
                    registerLayout.visibility=View.GONE
                    noRegisterLayout.visibility=View.VISIBLE
                    profilLayout.visibility=View.GONE
                    eduCenterLayout.visibility=View.GONE
                    logOutLayout.visibility=View.GONE
                    Toast.makeText(this, "Tizimdan chiqildi", Toast.LENGTH_LONG).show()
                }
                .show()
        }

        noRegisterLayout.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        //menu ni ochish
        openDriverMenu.setOnClickListener {
            draverLayout.openDrawer(GravityCompat.START)
        }

        editProfile.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }

        Support.setOnClickListener {
            startActivity(Intent(this, SupportActivity::class.java))
        }

        eduCenterLayout.setOnClickListener {
            startActivity(Intent(this, MyCentersActivity::class.java))
        }


        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==2002){
            loadData()
        } else{ }
    }

    private fun loadData() {
        viewModel.getUser()
    }

    fun getLocation(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
             return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            if(location!=null){
                val currentLocation = LocationModel(location.latitude,location.longitude)
                PrefUtils.setLocation(currentLocation)
                homeFragment.loadData()
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

}