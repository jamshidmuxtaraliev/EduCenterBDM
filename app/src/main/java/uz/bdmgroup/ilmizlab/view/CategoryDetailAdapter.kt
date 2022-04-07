package uz.bdmgroup.ilmizlab.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.category_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.SciencesModel
import uz.bdmgroup.ilmizlab.utils.Constants

interface CategoryDetailAdapterCallback {
    fun onClickItem(item: SciencesModel)
}

class CategoryDetailAdapter(
    val items: List<SciencesModel>,
    private var callback: CategoryDetailAdapterCallback
) : RecyclerView.Adapter<CategoryDetailAdapter.ItemHolder>() {
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.TitleCategory.text = item.title
        Glide.with(holder.itemView.iconCategory.context).load(Constants.HOSTING_IMAGE + item.icon)
            .into(holder.itemView.iconCategory)
        holder.itemView.setOnClickListener {
            items.forEach {
                it.checked = false
            }
            item.checked = true
            callback.onClickItem(item)
            notifyDataSetChanged()
        }
        if (item.checked == true) {
            holder.itemView.cardview.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.yellow)
            )
            holder.itemView.iconCategory.setColorFilter(ContextCompat.getColor(holder.itemView.context,R.color.white))
        } else {
            holder.itemView.cardview.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.white)
            )
            holder.itemView.iconCategory.setColorFilter(ContextCompat.getColor(holder.itemView.context,R.color.yellow))
        }

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}