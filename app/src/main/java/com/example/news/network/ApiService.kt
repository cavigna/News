package com.example.news.network


import com.example.news.BuildConfig
import com.example.news.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        val API_KEY = BuildConfig.API_KEY
    }

    //https://newsapi.org/v2/top-headlines?country=ar&apiKey=5edd886c15e34c75858bf7bf018d361f

    @GET("top-headlines")

    suspend fun traerUltimasNoticiasAr(
        @Query(value = "country") country: String = "ar",
        @Query(value = "apiKey") apiKey: String = API_KEY,
        @Query(value ="pageSize") pageSize:Int = 50
    ): NewsResponse
}