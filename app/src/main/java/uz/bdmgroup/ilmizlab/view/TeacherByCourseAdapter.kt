package uz.bdmgroup.ilmizlab.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.teacher_item_layout.view.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.TeacherModel

class TeacherByCourseAdapter (val items:List<TeacherModel>):RecyclerView.Adapter<TeacherByCourseAdapter.ItemHolder>(){
    class ItemHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.teacher_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item=items[position]
        holder.itemView.teacherName.text=item.name
        holder.itemView.mutaxasislik.text=item.specialization
        holder.itemView.tajribasi.text=item.experience.toString()+" yillik tajriba"
        holder.itemView.connectWithTeacher.setOnClickListener {
            val telegramUrl="${item.info_link}"
            val telegramIntent= Intent(Intent.ACTION_VIEW)
            telegramIntent.data= Uri.parse(telegramUrl)
            it.context.startActivity(telegramIntent)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}