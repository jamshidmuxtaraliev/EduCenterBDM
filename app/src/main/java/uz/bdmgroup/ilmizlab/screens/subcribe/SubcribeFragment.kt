package uz.bdmgroup.ilmizlab.screens.subcribe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_subscriber.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.LCFilterModel
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.utils.PrefUtils
import uz.bdmgroup.ilmizlab.view.EduCenterAdapter

class SubcribeFragment : Fragment() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscriber, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeSubscriber.setOnRefreshListener {
            loadData()
        }

        flNoRegister.visibility=if (PrefUtils.getToken().isNullOrEmpty()) View.VISIBLE else View.GONE
        flSubscriber.visibility=if (PrefUtils.getToken().isNullOrEmpty()) View.GONE else View.VISIBLE


        recylerSubscriber.layoutManager=GridLayoutManager(requireActivity(), 2)

        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(requireActivity(), Observer {
            swipeSubscriber.isRefreshing=it
            swipeSubscriber.visibility=if (it) View.GONE else View.VISIBLE
            flProgressSubscribe.visibility=if (it) View.VISIBLE else View.GONE
        })

        viewModel.topEduCenterData.observe(requireActivity(), Observer {
            recylerSubscriber.adapter=EduCenterAdapter(it)
        })

        loadData()
    }

    private fun loadData() {
        viewModel.getEduCenter(LCFilterModel(0,0,0, 0, "", "rating",
            2, 0.0, 0.0, true, ))
    }

    companion object {
        @JvmStatic
        fun newInstance() = SubcribeFragment()
    }
}