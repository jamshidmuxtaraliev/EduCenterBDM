package uz.bdmgroup.ilmizlab.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.kurs_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.CoursesModel
import uz.bdmgroup.ilmizlab.screens.TeacherActivity

class CoursesAdapter(val items: List<CoursesModel>) :
    RecyclerView.Adapter<CoursesAdapter.ItemHolder>() {
    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.kurs_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.nameKurs.text = item.name
        holder.itemView.priceKurs.text = item.monthly_payment
        holder.itemView.commentKurs.text = item.science.title

        holder.itemView.btnUstozlar.setOnClickListener {
            val intent=Intent(it.context, TeacherActivity::class.java)
            intent.putExtra("course_id", item.id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}