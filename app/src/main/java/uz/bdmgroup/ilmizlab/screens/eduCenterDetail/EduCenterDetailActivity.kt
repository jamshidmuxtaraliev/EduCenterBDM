package uz.bdmgroup.ilmizlab.screens.eduCenterDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_markaz_detail.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.EduCenterModel
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.screens.LevelActivity
import uz.bdmgroup.ilmizlab.screens.SeeMoreFragment
import uz.bdmgroup.ilmizlab.screens.registration.SignInActivity
import uz.bdmgroup.ilmizlab.utils.Constants
import uz.bdmgroup.ilmizlab.utils.PrefUtils
import uz.bdmgroup.ilmizlab.view.TabPageAdapter
// add commnett
//r42fr
//egbv3tgb3t
class EduCenterDetailActivity : AppCompatActivity() {
        lateinit var item: EduCenterModel
        lateinit var grade:String
        lateinit var viewModel: MainViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_markaz_detail)

        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

//kmlii
        imageCloseDetail.setOnClickListener {
            finish()
        }

        toolbar.setOnClickListener {
            finish()
        }
        //extra datani qabul qilib olish
        item = intent.getSerializableExtra("go_extra") as EduCenterModel
        commentEdu.text = item.comment
        nameEdu.text = item.name

        if(PrefUtils.getLocation()!=null){
            val currentLocation = PrefUtils.getLocation()
            val locationA = Location("gps")
            locationA.latitude = currentLocation!!.lat
            locationA.longitude = currentLocation!!.long

            val locationB = Location("gps")
            locationB.latitude = item.latitude
            locationB.longitude = item.longitude

            val distance: Float = locationA.distanceTo(locationB)
            tvDistance.text = String.format("%.1f", distance/1000) + " km"
        }

        val a =item.rating
        if (a<=3){
            grade="qoniqarli"
        }else if (a>3 && a<=4){
            grade="yaxshi"
        }else{
            grade="alo baho"
        }
        levelEdu.text=item.rating_count.toString()+" ta - "+item.rating.toString()+"/${grade}"
        countFollowerEdu.text=item.subsribers_count.toString()
        summaEdu.text=item.monthly_payment_min.toString()+" dan "+item.monthly_payment_max.toString()+" gacha"
        locationEdu.text=item.region.region_name+"/"+item.district.district_name+"/"+item.address
        phoneNumberEdu.text=item.phone
        commentEdu.text=item.comment
        Glide.with(imageEdu.context).load(Constants.HOSTING_IMAGE + item.main_image).into(imageEdu)
        collapsing_toolbar.title = item.name

        viewModel.error.observe(this, Observer {
//            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        //obuna bo'lish  /////////////////////////////////////////////////////////////
        viewModel.progress.observe(this, Observer {
            animationViewFollow.visibility=if (it) View.VISIBLE else View.GONE
        })

        viewModel.checkSubscriberData.observe(this, Observer {
            if (it==true){
                followingEdu.text="Obunani tugatish"
                followingEdu.setTextColor(resources.getColor(R.color.colorAccent))
            }else {
                followingEdu.text="Obuna bo'lish"
                followingEdu.setTextColor(resources.getColor(R.color.yellow))
            }
        })
        viewModel.subscriberData.observe(this, Observer {
            viewModel.getCheckSubscriber(item.id)
        })
        viewModel.getCheckSubscriber(item.id)



        followingEdu.setOnClickListener {
            if(PrefUtils.getToken().isNullOrEmpty()){
                startActivity(Intent(this, SignInActivity::class.java))
            }else{
                viewModel.setSubscriber(item.id)
            }
        }

///////////////////////////////////////////////////////////////////////////////////////////
        seeAllEduAbout.setOnClickListener {
            val fragment = SeeMoreFragment .newInstance(item)
            fragment.show(supportFragmentManager, fragment.tag)
        }

        //baholash
        levelingEdu.setOnClickListener {
            val intent = Intent(nameEdu.context, LevelActivity::class.java)
            intent.putExtra("go_Level", item)
            startActivity(intent)
        }

        //xaritadan ko'rish
        viewMapEdu.setOnClickListener {
            val uri:Uri= Uri.parse("http://maps.google.com/maps?q=${item.latitude}, ${item.longitude}")
            val mapIntent=Intent(Intent.ACTION_VIEW, uri)
            startActivity(mapIntent)
        }

        //Nazad tugmasini kodi
        imageCloseDetail.setOnClickListener {
            finish()
        }

        //SHare qilish
        shareEdu.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share to: ")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "${nameEdu.text} oquv markazimizda o'qing, bizda to'lovlar: ${summaEdu.text} Biz bilan boglaning: ${phoneNumberEdu.text} Manzilimiz: ${locationEdu.text}"
            )
            startActivity(intent)
        }

        //Telefon qilish
        callingEdu.setOnClickListener {
            val mobilNumber = phoneNumberEdu.text.toString().trim()
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:" + "${mobilNumber}")
            startActivity(phoneIntent)
        }

        //TabLayout ga adapter berish
        viewPager.adapter = TabPageAdapter(supportFragmentManager, item)
        tablayout.setupWithViewPager(viewPager)


    }

}