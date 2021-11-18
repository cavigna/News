package com.example.news.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.application.NewsApp
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.listadapter.NewsListAdapter
import com.example.news.viewmodel.NewsModelFactory
import com.example.news.viewmodel.NewsViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var application: Application

    private val viewModel by activityViewModels<NewsViewModel> {
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
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        val recyclerView = binding.recyclerView
        val adapter = NewsListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel.listadoNewsDB.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })





        return binding.root

    }


}