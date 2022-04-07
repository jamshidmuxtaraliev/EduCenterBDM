package uz.bdmgroup.ilmizlab.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.district_item_layout.view.*
import kotlinx.android.synthetic.main.region_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.DistrictSearchModel
import uz.bdmgroup.ilmizlab.model.RegionSearchModel



class RegionSelectAdapter(val items: List<RegionSearchModel>) :
    RecyclerView.Adapter<RegionSelectAdapter.ItemHolder>() {
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.region_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.regionName.text = item.name_uz
        holder.itemView.recyclerDistrict.visibility=if (item.checked) View.VISIBLE else View.GONE
        holder.itemView.openRegion.setImageResource(if (item.checked) R.drawable.ic_baseline_arrow_drop_up_24 else R.drawable.ic_baseline_arrow_drop_down_24)
        holder.itemView.recyclerDistrict.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.itemView.recyclerDistrict.adapter=DistrictSelectAdapter(item.districts, object:SelectedItemCallback{
            override fun onCheckedItem(item: DistrictSearchModel) {
                items.forEach {
                    it.districts.forEach {
                        it.checked=false
                    }
                }
                item.checked=true
                notifyDataSetChanged()
            }
        })
        holder.itemView.setOnClickListener {
          item.checked = !item.checked
            notifyItemChanged(position)
       }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}