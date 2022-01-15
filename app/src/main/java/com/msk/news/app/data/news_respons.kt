package com.msk.news.app.data

data class news_respons(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)