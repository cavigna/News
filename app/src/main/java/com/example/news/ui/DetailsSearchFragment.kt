package com.example.news.ui

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.news.R
import com.example.news.application.NewsApp
import com.example.news.databinding.FragmentDetailsSearchBinding
import com.example.news.model.db.NewsFavEntity
import com.example.news.utils.formateameLaFecha
import com.example.news.viewmodel.NewsModelFactory
import com.example.news.viewmodel.NewsViewModel


class DetailsSearchFragment : Fragment() {

    private lateinit var binding: FragmentDetailsSearchBinding
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
        binding = FragmentDetailsSearchBinding.inflate(layoutInflater, container, false)





        viewModel.noticiaBuscadaDetalles.observe(viewLifecycleOwner, { article ->

            with(binding) {
                imageView2.load(article.urlToImage)
                textViewTitulo.text = article.title.substringBefore("-")
                textViewDesc.text = article.description
                textViewContenido.text = article.content.substringBefore("[")
            }

        })

        binding.imageviewFavorito.setOnClickListener {
            agregarFavorito()
            Toast.makeText(requireContext(), "Noticia agregada", Toast.LENGTH_SHORT).show()



        }

        binding.buttonLink.setOnClickListener {
            val urlNoticia: String = viewModel.noticiaBuscadaDetalles.value!!.url

            openWebPage(urlNoticia)
        }

        return binding.root
    }

    private fun agregarFavorito() {
        val newsDetail = viewModel.noticiaBuscadaDetalles.value
        val newsFav = newsDetail?.let {
            NewsFavEntity(
                fuente = newsDetail.source.name,
                titulo = newsDetail.title,
                descripcion = it.description,
                url = newsDetail.url,
                imagenUrl = newsDetail.urlToImage,
                contenido = newsDetail.content,
                fecha = formateameLaFecha(newsDetail.publishedAt)!!,
            )
        }

        newsFav?.let { viewModel.agregarFavorito(it) }


    }


    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(activity?.packageManager!!) == null) {
            startActivity(intent)
        }
    }
}