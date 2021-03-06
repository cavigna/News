package com.example.news.network


import com.example.news.BuildConfig
import com.example.news.model.NetworkResult
import com.example.news.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        val API_KEY = BuildConfig.API_KEY
    }



    @GET("top-headlines")

    suspend fun traerUltimasNoticiasAr(
        @Query(value = "country") country: String = "ar",
        @Query(value = "apiKey") apiKey: String = API_KEY,
        @Query(value = "pageSize") pageSize: Int = 50
    ): NewsResponse



    @GET("everything")
    suspend fun buscarNoticia(
        @Query(value = "q", encoded = true) q: String,
        @Query("sortBy") sortBy: String="publishedAt",
        @Query(value = "apiKey") apiKey: String = API_KEY,
        @Query(value = "pageSize") pageSize: Int = 50
    ):NewsResponse
}



/*
    @GET("everything")
    suspend fun buscarNoticia(
        @Query(value = "q", encoded = true) q: String,
        @Query("sortBy") sortBy: String="popularity",
        @Query(value = "apiKey") apiKey: String = API_KEY,
        @Query(value = "pageSize") pageSize: Int = 50
    ):NewsResponse
 */