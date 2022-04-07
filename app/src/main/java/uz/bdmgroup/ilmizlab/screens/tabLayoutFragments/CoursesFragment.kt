package uz.bdmgroup.ilmizlab.screens.tabLayoutFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_kurslar.*
import kotlinx.android.synthetic.main.kurs_item_layout.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.EduCenterModel
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.screens.TeacherActivity
import uz.bdmgroup.ilmizlab.view.CoursesAdapter

class CoursesFragment(var item: EduCenterModel) : Fragment() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kurslar, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerKurslar.layoutManager = LinearLayoutManager(requireActivity())
        recyclerKurslar.adapter=CoursesAdapter(item.courses)
    }
}