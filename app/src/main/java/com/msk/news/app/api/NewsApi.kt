package com.msk.news.app.api

import com.msk.news.app.Util.Constants.Companion.API_KEy
import com.msk.news.app.data.newsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET ("1/news?apikey=pub_3553f0c0b92d815c627ed37e70ad3bd7e4ad")
    suspend fun getNews(
        @Query("country")
        countryCode:String="us",
        @Query("page")
        pagenunber:Int=1,
        @Query("api_key")
        apikey:String=API_KEy
    ):Response<newsResponse>


    @GET ("1/news?apikey=pub_3553f0c0b92d815c627ed37e70ad3bd7e4ad")
    suspend fun searchNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        pagenunber:Int=1,
        @Query("api_key")
        apikey:String=API_KEy
    ):Response<newsResponse>
}