package uz.bdmgroup.ilmizlab.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.markaz_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.EduCenterModel
import uz.bdmgroup.ilmizlab.screens.eduCenterDetail.EduCenterDetailActivity
import uz.bdmgroup.ilmizlab.utils.Constants

class EduCenterAdapter(val items: List<EduCenterModel>) :
    RecyclerView.Adapter<EduCenterAdapter.ItemHolder>() {
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.markaz_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EduCenterDetailActivity::class.java)
            intent.putExtra("go_extra", item)
            it.context.startActivity(intent)
        }

        holder.itemView.StarCount.text = item.rating.toString().subSequence(0, 3)
        holder.itemView.LocationMarkazY.text = item.district.district_name
        holder.itemView.NomiMarkazY.text = item.name
        var kurslar: String = ""
        item.courses.forEach {
            kurslar += it.science.title + ","
        }
        holder.itemView.TypeMarkazY.text = kurslar
        holder.itemView.CommentMarkazY.text = item.comment
        Glide.with(holder.itemView.context).load(Constants.HOSTING_IMAGE + item.main_image)
            .into(holder.itemView.RasmiMarkazY)
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}