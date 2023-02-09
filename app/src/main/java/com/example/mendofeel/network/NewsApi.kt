package com.example.mendofeel.network

import com.example.mendofeel.model.NewsArticles
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNewsArticles(@Query("q") country: String, @Query("from") date: String, @Query("apiKey") key: String, @Query("sortBy") sortCriteria: String): NewsArticles
}