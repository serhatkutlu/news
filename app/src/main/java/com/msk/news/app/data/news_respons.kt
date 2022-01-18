package com.msk.news.app.data

data class news_respons(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)