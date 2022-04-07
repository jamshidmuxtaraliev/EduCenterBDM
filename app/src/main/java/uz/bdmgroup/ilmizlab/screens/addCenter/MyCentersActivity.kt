package uz.bdmgroup.ilmizlab.screens.addCenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_my_centers.*
import uz.bdmgroup.ilmizlab.R

class MyCentersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_centers)

        btnAddCenter.setOnClickListener {
            startActivity(Intent(this, AddCenterActivity::class.java))
        }
    }
}