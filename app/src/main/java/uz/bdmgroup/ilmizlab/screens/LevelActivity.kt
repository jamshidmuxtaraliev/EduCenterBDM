package uz.bdmgroup.ilmizlab.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_level.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.EduCenterModel
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.model.request.RatingRequest
import uz.bdmgroup.ilmizlab.screens.registration.SignInActivity
import uz.bdmgroup.ilmizlab.utils.PrefUtils

class LevelActivity : AppCompatActivity() {
    lateinit var iten:EduCenterModel
    lateinit var viewModel: MainViewModel

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        iten=intent.getSerializableExtra("go_Level")as EduCenterModel
        LvelEdeName.text=iten.name

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            flProgressLevel.visibility=if (it) View.VISIBLE else View.GONE
        })

        viewModel.makeRatingData.observe(this, Observer {
            flStar.visibility=View.VISIBLE
        })

        if (PrefUtils.getToken().isNullOrEmpty()){
            btnRating.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            btnRating.text="Ro'yhatdan o'tish"
            btnRating.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        }else{
            btnRating.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            btnRating.setTextColor(resources.getColor(R.color.white))
            btnRating.text="Yuborish"
        }

        closeLevel.setOnClickListener {
            finish()
        }

        btnRating.setOnClickListener{
            if (PrefUtils.getToken().isNullOrEmpty()){
                startActivity(Intent(this, SignInActivity::class.java))
            }else{
                viewModel.makeRating(RatingRequest(ratingBar.rating, commentRating.text.toString(), iten.id))
                }
        }

        closeStartContainer.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            flStar.visibility=View.GONE
        }
    }
}