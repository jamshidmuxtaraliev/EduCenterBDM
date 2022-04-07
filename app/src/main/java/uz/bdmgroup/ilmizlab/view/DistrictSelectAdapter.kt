package uz.bdmgroup.ilmizlab.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.district_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.DistrictSearchModel
import uz.bdmgroup.ilmizlab.model.RegionSearchModel

interface SelectedItemCallback{
    fun onCheckedItem(item: DistrictSearchModel)
}

class DistrictSelectAdapter(val items: List<DistrictSearchModel>, private var callback: SelectedItemCallback) :RecyclerView.Adapter<DistrictSelectAdapter.ItemHolder>(){
    class ItemHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.district_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.districtName.text=item.name_uz
        holder.itemView.radioButton.isChecked=item.checked
        holder.itemView.setOnClickListener {
            callback.onCheckedItem(item)
        }

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}