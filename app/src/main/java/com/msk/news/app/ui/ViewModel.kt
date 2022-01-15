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
    val newsPage=1
init {
    getNews("tr")


}
    fun getNews(countryCode:String)=viewModelScope.launch {
        println(1)
        breakingNews.postValue(Resource.Loading())
        println(2)
        val response=newsRepository.getBreakingNews(countryCode, newsPage)
        println(3)
        breakingNews.postValue(handleBreakingNewsResponse(response))
        println(4)
    }

    private fun handleBreakingNewsResponse(response:Response<news_respons>):Resource<news_respons>{
        if (response.isSuccessful){
            response.body()?.let {
                println(it.articles)
                return Resource.Succes(it)


            }
        }
        return Resource.Error(response.message())
    }

}