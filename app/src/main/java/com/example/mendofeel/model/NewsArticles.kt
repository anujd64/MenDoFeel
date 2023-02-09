package com.example.mendofeel.model

data class NewsArticles(
    val articles: List<Article> = emptyList(),
    val status: String = "",
    val totalResults: Int = 0
)