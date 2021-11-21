package com.example.news.ui

import android.app.Application
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

        viewModel.chequearSiEsFav()

        viewModel.chequeResultado.observe(viewLifecycleOwner, {
            if (it){
                binding.imageviewFavorito.setImageResource(R.drawable.ic_baseline_bookmark_24)
            }
        })

        viewModel.noticiaBuscadaDetalles.observe(viewLifecycleOwner, { article ->

            with(binding) {
                imageView2.load(article.urlToImage)
                textViewTitulo.text = article.title.substringBefore("-")
                textViewDesc.text = article.description
                textViewContenido.text = article.content.substringBefore("[")
            }

        })

        binding.imageviewFavorito.setOnClickListener {
            //agregarFavorito()
            Toast.makeText(requireContext(), "Noticia agregada", Toast.LENGTH_SHORT).show()
           it.setBackgroundResource(R.drawable.ic_baseline_bookmark_24)

        }

        return binding.root
    }

    private fun agregarFavorito() {
        val newsDetail = viewModel.noticiaSelecionada.value
        val newsFav = NewsFavEntity(
            fuente = newsDetail?.fuente!!,
            titulo = newsDetail.titulo,
            descripcion = newsDetail.descripcion,
            url = newsDetail.url,
            imagenUrl = newsDetail.imagenUrl,
            contenido = newsDetail.contenido,
            fecha = newsDetail.fecha,
        )

        viewModel.agregarFavorito(newsFav)


    }


}