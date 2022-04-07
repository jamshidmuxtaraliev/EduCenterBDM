package uz.bdmgroup.ilmizlab.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.markaz_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.NewsModel
import uz.bdmgroup.ilmizlab.utils.Constants

class NewsDetailActivity : AppCompatActivity() {
    lateinit var item:NewsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        closeNewsDetail.setOnClickListener {
            finish()
        }

        item=intent.getSerializableExtra("go_extra")as NewsModel
        titleNewsDetail.text=item.title
        newsText.text=item.content
        Glide.with(newsPicture.context).load(Constants.HOSTING_IMAGE+item.image).into(newsPicture)
    }
}