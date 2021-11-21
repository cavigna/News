package com.example.news.viewmodel

import androidx.lifecycle.*
import com.example.news.model.Article
import com.example.news.model.NewsResponse
import com.example.news.model.db.NewsEntity
import com.example.news.model.db.NewsFavEntity
import com.example.news.repository.Repositorio
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NewsViewModel(private val repositorio: Repositorio) : ViewModel() {

    val listadoNoticiasPrueba = MutableLiveData<List<Article>>()

    val listadoNewsDB = repositorio.listarNoticiasDB().asLiveData()

    var noticiaSelecionada = MutableLiveData<NewsEntity>()
    var noticiaFavSelecionada = MutableLiveData<NewsFavEntity>()

    var noticiaBuscadaDetalles = MutableLiveData<Article>()

    val listadoFavoritos = repositorio.listarFavorito().asLiveData()

    init {
       // agregarListadoDB()
    }


    fun agregarListadoDB() {
        viewModelScope.launch(IO) {
        repositorio.agregarUltimasNoticasDB()
        }
    }

    fun traerUltimasNoticiasAr() {
        viewModelScope.launch(IO) {
            listadoNoticiasPrueba.postValue(
                repositorio.traerUltimasNoticiasAr().articles
            )
        }
    }

    fun agregarFavorito(favorito: NewsFavEntity){
        viewModelScope.launch(IO) {
            repositorio.agregarFavorito(favorito)
        }
    }

    fun eliminarFavorito(favorito: NewsFavEntity){
        viewModelScope.launch(IO) {
            repositorio.eliminarFavorito(favorito)
        }
    }

    val resultadoBusqueda = MutableLiveData<NewsResponse>()

    fun buscarNoticia(query: String){
        viewModelScope.launch(IO){
            resultadoBusqueda.postValue(repositorio.buscarNoticia(query))
        }

    }
}


class NewsModelFactory(private val repositorio: Repositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repositorio) as T
    }
}