package com.example.news.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "news_fav_table")
data class NewsFavEntity(

    val fuente: String = "",
    val titulo:String = "",
    val descripcion:String = "",
    val url:String = "",
    val imagenUrl:String = "",
    val contenido:String = "",

    @PrimaryKey
    val fecha : Date = (Calendar.getInstance().timeInMillis as Date)
)
