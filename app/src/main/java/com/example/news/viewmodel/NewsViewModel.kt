package com.example.news.viewmodel

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.*
import com.example.news.model.Article
import com.example.news.model.NewsResponse
import com.example.news.model.db.NewsEntity
import com.example.news.model.db.NewsFavEntity
import com.example.news.repository.Repositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class NewsViewModel(private val repositorio: Repositorio) : ViewModel() {

    val listadoNewsDB = repositorio.listarNoticiasDB().asLiveData()

    var noticiaSelecionada = MutableLiveData<NewsEntity>()
    var noticiaFavSelecionada = MutableLiveData<NewsFavEntity>()

    var noticiaBuscadaDetalles = MutableLiveData<Article>()

    val listadoFavoritos = repositorio.listarFavorito().asLiveData()

    init {
       agregarListadoDB() // Al iniciar, se agregará la respuesta de la api a la DB
    }


    private fun agregarListadoDB() {
        viewModelScope.launch(IO) {
        try {
            repositorio.agregarUltimasNoticasDB()
        }catch (e: NetworkErrorException){ }
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

/*
    fun chequearSiEsFav():Boolean{
        viewModelScope.launch(IO){
            val fechaNoticiaSeleccionada = noticiaSelecionada.value?.fecha
            val fechaNoticiaDB = fechaNoticiaSeleccionada?.let { repositorio.chequearSiEsFav(it) }
            val result = withContext(Dispatchers.Default){
                fechaNoticiaSeleccionada == fechaNoticiaDB
            }


        }
        return result
    }




    val pruebaMutable = noticiaSelecionada.value?.let { repositorio.chequearSiEsFav(it.url) }

    fun prueba():List<String>{
        val fecha: String? = noticiaSelecionada.value?.url
        // val fechaNoticiaDB = fechaNoticiaSeleccionada?.let { repositorio.chequearSiEsFav(it) }
        //val fechaNoticiaDB = repositorio.chequearSiEsFav(fecha).asLiveData().value!!.fecha

        val uno = noticiaSelecionada.value!!.url
        val dos = fecha?.let { repositorio.chequearSiEsFav(it).asLiveData().value }?.url

        return listOf(uno, dos.toString())

    }
 */


/*



    var  chequeResultado = MutableLiveData(false)


    fun chequearSiEsFav(){
        viewModelScope.launch(IO){
            val fecha: String? = noticiaSelecionada.value?.url
           // val fechaNoticiaDB = fechaNoticiaSeleccionada?.let { repositorio.chequearSiEsFav(it) }
            //val fechaNoticiaDB = repositorio.chequearSiEsFav(fecha).asLiveData().value!!.fecha

            val uno = noticiaSelecionada.value!!.url
            val dos = fecha?.let { repositorio.chequearSiEsFav(it).asLiveData().value }?.url

            if (uno == dos){
                chequeResultado.postValue(true)

            }

            Log.i("pinchila", uno)
            Log.i("pinchila", dos.toString())


        }

    }
 */