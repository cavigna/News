package com.example.news.ui

import android.app.Application
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide

import com.example.news.application.NewsApp
import com.example.news.databinding.FragmentDetailsBinding
import com.example.news.databinding.FragmentDetailsBinding.inflate

import com.example.news.listadapter.NewsListAdapter
import com.example.news.viewmodel.NewsModelFactory
import com.example.news.viewmodel.NewsViewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
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
        binding = inflate(layoutInflater, container, false)


        viewModel.listadoNewsDB.observe(viewLifecycleOwner,{
            val news = it[5]

            with(binding){
                imageView2.load(news.imagenUrl)
                textViewTitulo.text = news.titulo.substringBefore("-")
                textViewDesc.text = news.descripcion
                textViewContenido.text = news.contenido
            }
        })






        return binding.root

    }


}