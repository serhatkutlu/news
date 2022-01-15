package com.msk.news.app.api

import com.msk.news.app.Util.Constants.Companion.API_KEy
import com.msk.news.app.data.news_respons
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET ("v2/top-headlines")
    suspend fun getNews(
        @Query("apiKey")
        api_key:String=API_KEy,
        @Query("country")
        countryCode:String="tr",
        @Query("page")
        pagenunber:String="1",
    ):Response<news_respons>


    @GET ("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        page:Int=1,
        @Query("apiKey")
        apikey:String=API_KEy
    ):Response<news_respons>
}