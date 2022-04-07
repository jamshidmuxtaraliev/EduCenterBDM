package uz.bdmgroup.ilmizlab.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.category_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.CategoryModel
import uz.bdmgroup.ilmizlab.screens.eduCenterDetail.EduCenterDetailActivity
import uz.bdmgroup.ilmizlab.screens.filterByCategory.FilterByCategoryActivity
import uz.bdmgroup.ilmizlab.utils.Constants

class CategoryAdapter(val items: List<CategoryModel>):RecyclerView.Adapter<CategoryAdapter.ItemHolder>(){
    class ItemHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item=items[position]
        holder.itemView.TitleCategory.text=item.title
        Glide.with(holder.itemView.context).load(Constants.HOSTING_IMAGE+item.icon).into(holder.itemView.iconCategory)
        holder.itemView.setOnClickListener {
            val intent=Intent(it.context, FilterByCategoryActivity::class.java)
            intent.putExtra("go_category", item)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}