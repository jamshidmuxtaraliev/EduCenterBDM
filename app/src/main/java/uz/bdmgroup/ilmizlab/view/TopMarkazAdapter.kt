package uz.bdmgroup.ilmizlab.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.markaz_item_layout.view.*
import kotlinx.android.synthetic.main.top_markaz_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.EduCenterModel
import uz.bdmgroup.ilmizlab.screens.eduCenterDetail.EduCenterDetailActivity
import uz.bdmgroup.ilmizlab.utils.Constants

class TopMarkazAdapter(val items: List<EduCenterModel>) : RecyclerView.Adapter<EduCenterAdapter.ItemHolder>() {
    class ItemHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EduCenterAdapter.ItemHolder {
        return EduCenterAdapter.ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.top_markaz_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: EduCenterAdapter.ItemHolder, position: Int) {
        val item=items[position]

        holder.itemView.setOnClickListener {
            val intent= Intent(it.context, EduCenterDetailActivity::class.java)
            intent.putExtra("go_extra", item)
            it.context.startActivity(intent)
        }
        holder.itemView.gradeEduCenter.text= item.rating.toString().subSequence(0, 3)
        holder.itemView.locationEdeCenter.text=item.district.district_name
        holder.itemView.titleEduCenter.text=item.name
        holder.itemView.textEduCenter.text=item.name
        holder.itemView.CommentEduCenter.text=item.comment
        Glide.with(holder.itemView.context).load(Constants.HOSTING_IMAGE+item.main_image).into(holder.itemView.ImageEduCenter)

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}