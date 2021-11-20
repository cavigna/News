package com.example.news.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.application.NewsApp
import com.example.news.databinding.FragmentSearchBinding
import com.example.news.listadapter.SearchListAdapter
import com.example.news.viewmodel.NewsModelFactory
import com.example.news.viewmodel.NewsViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var application: Application
    private val viewModel by viewModels<NewsViewModel> {
        NewsModelFactory((application as NewsApp).repositorio)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = requireActivity().application
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        val searchView = binding.searchView

        val recyclerView = binding.recyclerViewSearch
        val adapter = SearchListAdapter()
        recyclerView.adapter =  adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                p0?.let { viewModel.buscarNoticia(it) }

                viewModel.resultadoBusqueda.observe(viewLifecycleOwner, {
                    if (it.totalResults > 0){
                        adapter.submitList(it.articles)
                    }
                })


                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean = true

        })


        return binding.root
    }


}