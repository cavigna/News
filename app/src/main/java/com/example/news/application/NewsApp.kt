package com.example.news.application

import android.app.Application
import com.example.news.db.BaseDeDatos
import com.example.news.network.ApiService
import com.example.news.repository.Repositorio
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApp: Application() {

        private val retrofitNewsClient  by lazy {
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        }

    private val database by lazy { BaseDeDatos.getDataBase(this) }

        val repositorio by lazy { Repositorio(retrofitNewsClient, database.dao()) }
}