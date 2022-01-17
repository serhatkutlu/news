package com.msk.news.app.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.msk.news.R
import com.msk.news.app.ui.MainActivity
import com.msk.news.app.ui.ViewModel
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment() {
lateinit var viewModel:ViewModel
val args:ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        val article=args.article
        Log.d("sds","6")
       webView.apply {
            webViewClient=WebViewClient()

                loadUrl(article.url)

        }

    }


}