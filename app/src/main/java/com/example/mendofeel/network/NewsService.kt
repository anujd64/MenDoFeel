package com.example.mendofeel.network

import com.example.mendofeel.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NewsService{
    fun getNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}

