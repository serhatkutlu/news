package com.msk.news.app.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.news.app.Util.Resource
import com.msk.news.app.data.newsResponse


import com.msk.news.app.repostory.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel(
    val newsRepository: NewsRepository
) :ViewModel(){
    val breakingNews:MutableLiveData<Resource<newsResponse>> = MutableLiveData()
    val newsPage=1
init {
    getNews("")
}
    fun getNews(countryCode:String)=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response=newsRepository.getBreakingNews(countryCode, newsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response:Response<newsResponse>):Resource<newsResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Succes(it)

            }
        }
        return Resource.Error(response.message())
    }

}