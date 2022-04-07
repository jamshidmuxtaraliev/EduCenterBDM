package uz.bdmgroup.ilmizlab.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.grade_by_user_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.GradeModel

class RatingUserAdapter(val items: List<GradeModel>) :
    RecyclerView.Adapter<RatingUserAdapter.ItemHolder>() {
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.grade_by_user_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.itemView.nameUserRating.text = item.user_fullname
        holder.itemView.ratingUserRating.rating= item.rating.toFloat()
        holder.itemView.timeUserRating.text = item.date
        holder.itemView.gradeUserRating.text = item.rating.toString()
        holder.itemView.commentUserRating.text = item.comment
        Glide.with(holder.itemView.pictureUserRating.context).load(item.user_avatar)
            .into(holder.itemView.pictureUserRating)
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}