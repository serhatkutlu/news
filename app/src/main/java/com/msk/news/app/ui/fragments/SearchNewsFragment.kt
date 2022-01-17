package com.msk.news.app.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msk.news.R
import com.msk.news.app.Util.Resource
import com.msk.news.app.adapter.recyclerAdapter
import com.msk.news.app.ui.MainActivity
import com.msk.news.app.ui.ViewModel
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment() {

lateinit var viewModel: ViewModel
lateinit var recycler_Adapter:recyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setupRecyclerAdapter()


        recycler_Adapter.SetOnItemClickListener {

            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            Log.d("sds","5")
            findNavController().navigate(

                R.id.action_searchNewsFragment_to_articleFragment2,bundle
            )
        }
        var job: Job?=null
        etSearch.addTextChangedListener{editible->
            job?.cancel()
            job= MainScope().launch {
                delay(500L)
                editible?.let {
                    if(editible.toString().isNotEmpty()){
                        viewModel.searchNews(editible.toString())
                    }
                }
            }

        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Succes->{
                    println(it.data)
                    hideProgressBar()
                    it.data?.let {newsResponse ->
                        println(newsResponse.articles)
                        recycler_Adapter.differ.submitList(newsResponse.articles)

                    }

                }
                is Resource.Error->{
                    println(it)
                    hideProgressBar()
                    it.message?.let { message->
                        println(message)

                    }
                }
                is Resource.Loading->{
                    println(it)
                    showProgressBar()
                }



            }

        })
    }


private fun hideProgressBar(){
    searchprogressBar2.visibility=View.INVISIBLE
}
private fun showProgressBar(){
    searchprogressBar2.visibility=View.INVISIBLE
}
private fun setupRecyclerAdapter(){
    recycler_Adapter= recyclerAdapter()
    searchrecyclerView.apply {

        this.adapter=recycler_Adapter
        this.layoutManager= LinearLayoutManager(activity)


    }


}
}

