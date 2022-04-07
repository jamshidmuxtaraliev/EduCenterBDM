package uz.bdmgroup.ilmizlab.screens

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_support.*
import uz.bdmgroup.ilmizlab.R

class SupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        backAbout.setOnClickListener {
            finish()
        }

        SupportCall.setOnClickListener {
                val phoneIntent= Intent(Intent.ACTION_DIAL)
                phoneIntent.data= Uri.parse("tel:"+"+998911210322")
                startActivity(phoneIntent)
        }

        SupportTelegram.setOnClickListener {
            val telegramUrl="https://t.me//jamshid_muxtoraliyev/"
            val telegramIntent=Intent(Intent.ACTION_VIEW)
            telegramIntent.data= Uri.parse(telegramUrl)
            startActivity(telegramIntent)
        }

        SupportInstagram.setOnClickListener {
            val instaUrl="https://instagram.com//_jamshid_jm"
            val instaINtent=Intent(Intent.ACTION_VIEW)
            instaINtent.data= Uri.parse(instaUrl)
            startActivity(instaINtent)
        }

        SupportFacebook.setOnClickListener {
            val facebookUrl="https://www.facebook.com/jamshid.muxtaraliev"
            val facebookINtent=Intent(Intent.ACTION_VIEW)
            facebookINtent.data= Uri.parse(facebookUrl)
            startActivity(facebookINtent)
        }

        SupportPinterest.setOnClickListener {
            val pinterestUrl="https://www.pinterest.com/jamshidmuxtaraliev"
            val pinteresINtent=Intent(Intent.ACTION_VIEW)
            pinteresINtent.data= Uri.parse(pinterestUrl)
            startActivity(pinteresINtent)
        }

        SupportEmail.setOnClickListener {
            val emailUrl="https://jamshidmuxtaraliev@gmail.com"
            val emailINtent=Intent(Intent.ACTION_VIEW)
            emailINtent.data= Uri.parse(emailUrl)
            startActivity(emailINtent)
        }

    }
}