package com.example.news.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.model.db.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarListadoNews(listadoNews: List<NewsEntity>)

    @Query("SELECT * FROM news_table")
    fun listarUltimasNoticias(): Flow<List<NewsEntity>>
}