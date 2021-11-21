package com.example.news.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.load

import com.example.news.application.NewsApp
import com.example.news.databinding.FragmentDetailsBinding
import com.example.news.databinding.FragmentDetailsBinding.bind
import com.example.news.databinding.FragmentDetailsBinding.inflate

import com.example.news.listadapter.NewsListAdapter
import com.example.news.model.db.NewsFavEntity
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


        viewModel.noticiaSelecionada.observe(viewLifecycleOwner, { news ->


            with(binding) {
                imageView2.load(news.imagenUrl)
                textViewTitulo.text = news.titulo.substringBefore("-")
                textViewDesc.text = news.descripcion
                textViewContenido.text = news.contenido.substringBefore("[")
            }
        })

        binding.imageviewFavorito.setOnClickListener {
            agregarFavorito()
            Toast.makeText(requireContext(), "Noticia agregada", Toast.LENGTH_SHORT).show()
        }



        val fb = binding.floatingActionButton

        fb.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"

            intent.putExtra("url", viewModel.noticiaSelecionada.value?.url)

            if (intent.resolveActivity(activity?.packageManager!!)!=null){
                startActivity(intent)
            }
        }


        return binding.root

    }

    private fun agregarFavorito(){
        val newsDetail = viewModel.noticiaSelecionada.value
        val newsFav = NewsFavEntity(
            fuente = newsDetail?.fuente!!,
            titulo = newsDetail.titulo,
            descripcion = newsDetail.descripcion ,
            url = newsDetail.url,
            imagenUrl = newsDetail.imagenUrl,
            contenido = newsDetail.contenido,
            fecha = newsDetail.fecha,
        )

        viewModel.agregarFavorito(newsFav)


    }





}