package com.example.news.db

import androidx.room.*
import com.example.news.model.db.NewsEntity
import com.example.news.model.db.NewsFavEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarListadoNews(listadoNews: List<NewsEntity>)

    @Query("SELECT * FROM news_table WHERE imagenUrl != '' AND contenido !='' ORDER BY fecha DESC")
    fun listarUltimasNoticias(): Flow<List<NewsEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarFavNews(favNews: NewsFavEntity)

    @Delete
    suspend fun borrarFavorito(favNews: NewsFavEntity)

    @Query("SELECT * FROM news_fav_table")
    fun listarFavoritos(): Flow<List<NewsFavEntity>>

    @Query("SELECT * FROM news_fav_table WHERE fecha=:url")
    fun chequearSiEsFav(url: String): Flow<NewsFavEntity>



}
/*
    @Query("SELECT * FROM news_fav_table WHERE fecha=:fecha")
    fun chequearSiEsFav(fecha: Date): Flow<NewsFavEntity>
 */
/*
    @Query("SELECT * FROM news_table")
    fun listarUltimasNoticias(): Flow<List<NewsEntity>>
 */