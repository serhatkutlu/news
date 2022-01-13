package com.msk.news.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

import com.msk.news.R

import com.msk.news.app.db.articleDB
import com.msk.news.app.repostory.NewsRepository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository= NewsRepository(articleDB.invoke(this))
        val viewModelProviderFactory= NewsViewModelProviderFactory(repository)

        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(ViewModel::class.java)

    }




}