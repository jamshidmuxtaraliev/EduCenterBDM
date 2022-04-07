package uz.bdmgroup.ilmizlab.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.news_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.NewsModel
import uz.bdmgroup.ilmizlab.screens.NewsDetailActivity
import uz.bdmgroup.ilmizlab.utils.Constants

class NewsAdapter(val items: List<NewsModel>) : RecyclerView.Adapter<NewsAdapter.ItemHolder>() {
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.itemView.nameEduNews.text = item.center_name
        holder.itemView.tittleOffer.text = item.title
        holder.itemView.timeOffer.text = item.created_at.subSequence(0, 10)
        Glide.with(holder.itemView.context).load(Constants.HOSTING_IMAGE + item.image)
            .into(holder.itemView.pictureOffer)
        Glide.with(holder.itemView.context).load(Constants.HOSTING_IMAGE + item.center_image)
            .into(holder.itemView.pictureEdu)


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsDetailActivity::class.java)
            intent.putExtra("go_extra", item)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}