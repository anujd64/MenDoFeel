package com.example.mendofeel.repository

import com.example.mendofeel.Constants
import com.example.mendofeel.network.NewsService
import com.example.mendofeel.model.NewsArticles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ArticleRepository {
    companion object{
        fun getArticles(): Flow<NewsArticles> = flow{
            val response = NewsService.getNewsApi()
                .getNewsArticles(
                    country ="india",
                    date = "2023-02-09",
                    key = Constants.API_KEY,
                    sortCriteria = "publishedAt")
            emit(response)

        }.flowOn(Dispatchers.IO)
    }
}