package uz.bdmgroup.ilmizlab.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_teacher.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.view.TeacherByCourseAdapter

class TeacherActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)
        viewModel=ViewModelProvider(this).get( MainViewModel::class.java)


        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            flProgressTeacher.visibility=if (it) View.VISIBLE else View.GONE
        })

        viewModel.teacherData.observe(this, Observer {
            recyclerTeachers.layoutManager=LinearLayoutManager(this)
            recyclerTeachers.adapter=TeacherByCourseAdapter(it)
        })
        
        loadData()
    }

    private fun loadData() {
        val course_id=intent.getIntExtra("course_id", 30)
        viewModel.getTeacherByCourse(course_id)
    }
}