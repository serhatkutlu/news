package com.msk.news.app.repostory

import com.msk.news.app.api.retrofitInstance
import com.msk.news.app.db.articleDB
import retrofit2.Retrofit

class NewsRepository(
    val db:articleDB
) {
    suspend fun getBreakingNews(countryCode:String,pagenunber:Int)=
        retrofitInstance.api.getNews(pagenunber = pagenunber.toString())
}