package uz.bdmgroup.ilmizlab.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.district_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.SciencesModel
interface FanDetailCallback{
    fun onClickFan(item:SciencesModel)
}

class FanDetailAdapter(val items: List<SciencesModel>, var callback:FanDetailCallback) : RecyclerView.Adapter<FanDetailAdapter.ItemHolder>(){
    class ItemHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.district_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.districtName.text=item.title
        holder.itemView.radioButton.isChecked=item.checked
        holder.itemView.setOnClickListener {
           callback.onClickFan(item)
        }

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}