package com.example.news.repository

import com.example.news.db.NewsDao
import com.example.news.model.db.NewsEntity
import com.example.news.network.ApiService
import com.example.news.utils.Converters
import java.text.SimpleDateFormat
import java.util.*

class Repositorio(private val api: ApiService,private val dao: NewsDao) {

    suspend fun traerUltimasNoticiasAr() = api.traerUltimasNoticiasAr()

    suspend fun agregarUltimasNoticasDB(){
        val listadoAgregarDB = mutableListOf<NewsEntity>()
        val listadoApi = traerUltimasNoticiasAr().articles

        listadoApi.forEach { art->

            listadoAgregarDB.add(
                NewsEntity(
                    fuente = art.source.name,
                    titulo = art.title,
                    descripcion = art.description ?: "",
                    url = art.url,
                    imagenUrl = art.urlToImage ?: "",
                    contenido = art.content?:"",
                    fecha = formateameLaFecha(art.publishedAt)!!
                )
            )

        }

        dao.agregarListadoNews(listadoAgregarDB)
    }

     fun listarNoticiasDB() = dao.listarUltimasNoticias()
}

fun formateameLaFecha(fechaLoca: String): Date?{
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = sdf.parse(fechaLoca)
    return date
}