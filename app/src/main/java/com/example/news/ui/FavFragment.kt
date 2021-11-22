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
import com.example.news.databinding.FragmentFavBinding
import com.example.news.listadapter.FavListAdapter
import com.example.news.model.db.NewsFavEntity
import com.example.news.viewmodel.NewsModelFactory
import com.example.news.viewmodel.NewsViewModel

class FavFragment : Fragment(), FavListAdapter.MiBorradorDeNoticias {
    private lateinit var binding: FragmentFavBinding
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

        binding = FragmentFavBinding.inflate(layoutInflater, container, false)


        val recyclerView = binding.recyclerViewFav
        val adapter = FavListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.listadoFavoritos.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun alClick(favorito: NewsFavEntity) {
        viewModel.noticiaFavSelecionada.value = favorito
    }

    override fun borrar(favorito: NewsFavEntity) {
        viewModel.eliminarFavorito(favorito)
    }


}