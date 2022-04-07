package uz.bdmgroup.ilmizlab.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about.*
import uz.bdmgroup.ilmizlab.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        backAbout.setOnClickListener {
            finish()
        }
    }
}