package com.msk.news.app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.news.app.Util.Resource
import com.msk.news.app.data.Article
import com.msk.news.app.data.news_respons


import com.msk.news.app.repostory.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel(
    val newsRepository: NewsRepository
) :ViewModel(){
    val breakingNews:MutableLiveData<Resource<news_respons>> = MutableLiveData()
    val breakingnewsPage=1
    val searchNews:MutableLiveData<Resource<news_respons>> = MutableLiveData()
    val searchnewsPage=1
init {
    getNews("us")


}
    fun getNews(countryCode:String)=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response=newsRepository.getBreakingNews(countryCode, breakingnewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    fun searchNews(searchQuery:String)=viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response=newsRepository.searchNews(searchQuery,searchnewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }
    private fun handleBreakingNewsResponse(response:Response<news_respons>):Resource<news_respons>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Succes(it)


            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response:Response<news_respons>):Resource<news_respons>{
        if (response.isSuccessful){
            response.body()?.let {
                println(it.articles)
                return Resource.Succes(it)


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
}