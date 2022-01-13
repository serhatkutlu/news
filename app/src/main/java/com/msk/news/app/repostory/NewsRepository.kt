package com.msk.news.app.repostory

import com.msk.news.app.api.retrofitInstance
import com.msk.news.app.db.articleDB
import retrofit2.Retrofit

class NewsRepository(
    val db:articleDB
) {
    suspend fun getBreakingNews(countrtCode:String,pageNunber:Int)=
        retrofitInstance.api.getNews(countrtCode,pageNunber)
}