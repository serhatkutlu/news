package com.msk.news.app.repostory

import com.msk.news.app.api.retrofitInstance
import com.msk.news.app.data.Article
import com.msk.news.app.db.articleDB
import retrofit2.Retrofit

class NewsRepository(
    val db:articleDB
) {
    suspend fun getBreakingNews(countryCode:String,pagenunber:Int)=
        retrofitInstance.api.getNews(countryCode = countryCode,pagenunber = pagenunber.toString())

    suspend fun  searchNews(searchQuery:String,pagenunber: Int)=
        retrofitInstance.api.searchNews(searchQuery,pagenunber)

    suspend fun upsert(article:Article)=db.getArticleDao().upsert(article)

    fun getSavedNews()=db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article)=db.getArticleDao().deleteArticle(article)
}
