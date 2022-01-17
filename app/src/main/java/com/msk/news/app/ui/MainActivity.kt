package com.msk.news.app.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.msk.news.R

import com.msk.news.app.db.articleDB
import com.msk.news.app.repostory.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository= NewsRepository(articleDB(this))
        val viewModelProviderFactory= NewsViewModelProviderFactory(repository)
        viewModel=ViewModelProvider(this,viewModelProviderFactory).get(ViewModel::class.java)
        var navhost=supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navcontroller=navhost.navController
        bottomNavigationView.setupWithNavController(navcontroller)
    }





}