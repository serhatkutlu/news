package com.msk.news.app.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msk.news.R
import com.msk.news.app.Util.Constants
import com.msk.news.app.Util.Resource
import com.msk.news.app.adapter.recyclerAdapter
import com.msk.news.app.ui.MainActivity
import com.msk.news.app.ui.ViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment() {

lateinit var viewModel: ViewModel
lateinit var recycler_Adapter:recyclerAdapter
    var isLoading=false
    var isLastPage=false
    var isScrolling=false

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
                    hideProgressBar()
                    it.data?.let {newsResponse ->
                        recycler_Adapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages=newsResponse.totalResults/ Constants.QUERY_PAGE_SIZE +2
                        isLastPage=viewModel.searchnewsPage==totalPages
                        if (isLastPage){
                            rv_BreakingNews.setPadding(0,0,0,0)
                        }
                    }

                }
                is Resource.Error->{

                    hideProgressBar()
                    it.message?.let { message->
                        println(message)

                    }
                }
                is Resource.Loading->{

                    showProgressBar()
                }



            }

        })
    }
    val scrollListener= object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            var layoutManager=recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition=layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount=layoutManager.childCount
            val totalItemCount=layoutManager.itemCount

            val isNotLoadingAndNotLastPage=!isLastPage&&!isLoading
            val isAtLastItem=firstVisibleItemPosition+visibleItemCount>=totalItemCount
            val isNotAtBeginning=firstVisibleItemPosition>=0
            val isTotalMoreThanVisible=totalItemCount>= Constants.QUERY_PAGE_SIZE

            val shouldPaginate=isNotLoadingAndNotLastPage && isAtLastItem
                    &&isNotAtBeginning&&isTotalMoreThanVisible
            if (shouldPaginate){
                viewModel.searchNews(etSearch.text.toString())
                isScrolling=false
            }



        }
    }

private fun hideProgressBar(){
    searchprogressBar2.visibility=View.INVISIBLE
    isLoading=false
}
private fun showProgressBar(){
    searchprogressBar2.visibility=View.INVISIBLE
    isLoading=true
}
private fun setupRecyclerAdapter(){
    recycler_Adapter= recyclerAdapter()
    searchrecyclerView.apply {

        this.adapter=recycler_Adapter
        this.layoutManager= LinearLayoutManager(activity)
        addOnScrollListener(this@SearchNewsFragment.scrollListener)


    }


}
}

