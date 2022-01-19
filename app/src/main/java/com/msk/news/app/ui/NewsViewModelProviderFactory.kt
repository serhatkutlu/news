package com.msk.news.app.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msk.news.app.repostory.NewsRepository

class NewsViewModelProviderFactory(
    val app:Application,
    val newsRepository: NewsRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(app,newsRepository )as T
    }
}