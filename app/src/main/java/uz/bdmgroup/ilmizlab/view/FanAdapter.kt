package uz.bdmgroup.ilmizlab.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.region_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.CategoryModel
import uz.bdmgroup.ilmizlab.model.SciencesModel

class FanAdapter(val items: List<CategoryModel>): RecyclerView.Adapter<FanAdapter.ItemHolder>(){
    class ItemHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.region_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item=items[position]
        holder.itemView.regionName.text=item.title
        holder.itemView.recyclerDistrict.visibility=if (item.checked) View.VISIBLE else View.GONE
        holder.itemView.openRegion.setImageResource(if (item.checked) R.drawable.ic_baseline_arrow_drop_up_24
        else R.drawable.ic_baseline_arrow_drop_down_24)
        holder.itemView.recyclerDistrict.adapter=FanDetailAdapter(item.sciences, object :FanDetailCallback{
            override fun onClickFan(item: SciencesModel) {
              items.forEach {
                  it.sciences.forEach {
                      it.checked=false
                  }
              }
                item.checked=true
                notifyDataSetChanged()
            }
        })

        holder.itemView.setOnClickListener {
           item.checked=!item.checked
            notifyDataSetChanged()
        }
     }

    override fun getItemCount(): Int {
        return items.count()
    }
}