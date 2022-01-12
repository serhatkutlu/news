package com.msk.news.app.data

data class newsResponse(
    val nextPage: Int,
    val results: List<Result>,
    val status: String,
    val totalResults: Int
)