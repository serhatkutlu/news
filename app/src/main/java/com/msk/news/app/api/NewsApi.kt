package com.msk.news.app.api

import com.msk.news.app.Util.Constants.Companion.API_KEy
import com.msk.news.app.data.newsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET ("1/news?")
    suspend fun getNews(
        @Query("country")
        countryCode:String="us",
        @Query("page")
        pagenunber:Int=1,
        @Query("apikey")
        apikey:String=API_KEy
    ):Response<newsResponse>


    @GET ("1/news?")
    suspend fun searchNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        pagenunber:Int=1,
        @Query("apikey")
        apikey:String=API_KEy
    ):Response<newsResponse>
}