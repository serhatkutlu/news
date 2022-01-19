package com.msk.news.app.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msk.news.R
import com.msk.news.app.Util.Constants.Companion.QUERY_PAGE_SIZE
import com.msk.news.app.Util.Resource
import com.msk.news.app.adapter.recyclerAdapter
import com.msk.news.app.ui.MainActivity
import com.msk.news.app.ui.ViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment() {

    lateinit var viewModel: ViewModel
    lateinit var recycler_Adapter: recyclerAdapter
    var isLoading=false
    var isLastPage=false
    var isScrolling=false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerAdapter()

        recycler_Adapter.SetOnItemClickListener{

            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,bundle
            )
        }

        viewModel=(activity as MainActivity).viewModel

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Succes->{
                    hideProgressBar()
                    it.data?.let {newsResponse ->
                        recycler_Adapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages=newsResponse.totalResults/ QUERY_PAGE_SIZE +2
                        isLastPage=viewModel.breakingnewsPage==totalPages
                        if (isLastPage){
                            Toast.makeText(view.context,"reset",Toast.LENGTH_LONG).show()
                            rv_BreakingNews.setPadding(0,0,0,0)
                        }

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

    breakingprogressBar.visibility=View.INVISIBLE
    isScrolling=true
}
    private fun showProgressBar(){
    breakingprogressBar.visibility=View.INVISIBLE
        isScrolling=false
}
val scrollListener= object :RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
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
        val isTotalMoreThanVisible=totalItemCount>=QUERY_PAGE_SIZE

        val shouldPaginate=isNotLoadingAndNotLastPage && isAtLastItem
                &&isNotAtBeginning&&isTotalMoreThanVisible&&isScrolling
        if (shouldPaginate){
            viewModel.getNews("us")
            isScrolling=false
        }



    }
}

    private fun setupRecyclerAdapter(){
        recycler_Adapter= recyclerAdapter()
        rv_BreakingNews.apply {

            this.adapter=recycler_Adapter
            this.layoutManager= LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }


}