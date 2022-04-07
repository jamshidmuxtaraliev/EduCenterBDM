package uz.bdmgroup.ilmizlab.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.view.NewsAdapter

class NewsFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeNewsFragment.setOnRefreshListener {
            loadData()
        }

        recylerNews.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            swipeNewsFragment.isRefreshing = it
            swipeNewsFragment.visibility=if (it) View.GONE else View.VISIBLE
            recylerNews.visibility=if (it) View.GONE else View.VISIBLE
            flProgressNews.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.newsData.observe(requireActivity(), Observer {
            recylerNews.adapter = NewsAdapter(it)
        })

        loadData()
    }

    private fun loadData() {
        viewModel.getNews()
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}