package uz.bdmgroup.ilmizlab.screens.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.activity_splash.*
import uz.bdmgroup.ilmizlab.GpsUtils
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.LocationModel
import uz.bdmgroup.ilmizlab.screens.MainActivity
import uz.bdmgroup.ilmizlab.utils.PrefUtils


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var mLocationManager: LocationManager? = null


    private val TAG = "SPLASH_ACTIVITY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        supportActionBar?.hide()

        if (isNetworkConnected()==true){

            if (mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true){
                Handler().postDelayed({
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                }, 2000)
            }else{
                GpsUtils(this).enableLocationSettings(this)
            }
        }else{
            flDialog.visibility=View.VISIBLE
        }

        btnOkInternet.setOnClickListener {
            flDialog.visibility=View.GONE
        }
   }

    fun isNetworkConnected():Boolean{
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            Log.d(TAG, "onActivityResult:SUCCESS")
            Handler().postDelayed({
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }, 2000)
        } else {
            GpsUtils(this).enableLocationSettings(this)
        }
    }
}