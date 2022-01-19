package com.msk.news.app.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.news.app.NewsApplication
import com.msk.news.app.Util.Resource
import com.msk.news.app.data.Article
import com.msk.news.app.data.news_respons


import com.msk.news.app.repostory.NewsRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class ViewModel(
    app:Application,
    val newsRepository: NewsRepository
) :AndroidViewModel(app){
    val breakingNews:MutableLiveData<Resource<news_respons>> = MutableLiveData()
    var breakingnewsPage=1
    var breakingNewsResponce:news_respons?=null
    val searchNews:MutableLiveData<Resource<news_respons>> = MutableLiveData()
    var searchnewsPage=1
    var searchNewsResponce:news_respons?=null
    var newSearchQuery:String? = null

init {
    getNews("us")


}
    fun getNews(countryCode:String)=viewModelScope.launch {
        Log.d("sds","page:${searchnewsPage}")
        safeBreakingNewsCall(countryCode)
    }
    fun searchNews(searchQuery:String)=viewModelScope.launch {
    safeSearchNewsCall(searchQuery)
    }
    private fun handleBreakingNewsResponse(response:Response<news_respons>):Resource<news_respons>{
        if (response.isSuccessful){
            response.body()?.let {
                breakingnewsPage++
                if (breakingNewsResponce==null){
                    breakingNewsResponce=it
                }else{
                    val oldArticles=breakingNewsResponce?.articles
                    val newArticles=it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Succes(breakingNewsResponce ?: it)


            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response:Response<news_respons>):Resource<news_respons>{
        if (response.isSuccessful){
            response.body()?.let {
                searchnewsPage++
                if (searchNewsResponce==null){
                    searchNewsResponce=it
                }else{
                    val oldArticles=searchNewsResponce?.articles
                    val newArticles=it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Succes(searchNewsResponce ?: it)


            }
        }
        return Resource.Error(response.message())
    }
    fun saveArticle(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun getSavedNews()=newsRepository.getSavedNews()
    fun deleteArticle(article: Article)=viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
    private suspend fun safeSearchNewsCall(searchQuery: String) {
        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery, searchnewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeBreakingNewsCall(countryCode: String) {
        breakingNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.getBreakingNews(countryCode, breakingnewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}