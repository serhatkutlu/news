package com.msk.news.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.msk.news.R
import com.msk.news.app.Util.Resource
import com.msk.news.app.adapter.recyclerAdapter
import com.msk.news.app.ui.MainActivity
import com.msk.news.app.ui.ViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment() {

    lateinit var viewModel: ViewModel
    lateinit var recycler_Adapter: recyclerAdapter


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
        viewModel=(activity as MainActivity).viewModel

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
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

    progressBar.visibility=View.INVISIBLE
}
    private fun showProgressBar(){
    progressBar.visibility=View.INVISIBLE
}
    private fun setupRecyclerAdapter(){
        recycler_Adapter= recyclerAdapter()
        rv_BreakingNews.apply {

            this.adapter=recycler_Adapter
            this.layoutManager= LinearLayoutManager(activity)


        }


    }


}