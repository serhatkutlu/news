package com.msk.news.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msk.news.app.repostory.NewsRepository

class NewsViewModelProviderFactory(
    val newsRepository: NewsRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(newsRepository )as T
    }
}