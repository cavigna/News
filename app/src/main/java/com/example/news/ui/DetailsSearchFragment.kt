package com.example.news.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.news.R
import com.example.news.application.NewsApp
import com.example.news.databinding.FragmentDetailsSearchBinding
import com.example.news.viewmodel.NewsModelFactory
import com.example.news.viewmodel.NewsViewModel


class DetailsSearchFragment : Fragment() {

    private lateinit var binding: FragmentDetailsSearchBinding
    private lateinit var application: Application

    private val viewModel by activityViewModels<NewsViewModel>{
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
        binding = FragmentDetailsSearchBinding.inflate(layoutInflater, container, false)

        viewModel.noticiaBuscadaDetalles.observe(viewLifecycleOwner,{ article ->

            with(binding){
                imageView2.load(article.urlToImage)
                textViewTitulo.text = article.title.substringBefore("-")
                textViewDesc.text = article.description
                textViewContenido.text = article.content.substringBefore("[")
            }

        })

        return binding.root
    }


}