package com.msk.news.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msk.news.R
import com.msk.news.app.adapter.recyclerAdapter
import com.msk.news.app.ui.MainActivity
import com.msk.news.app.ui.ViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*


class SavedNewsFragment : Fragment() {

lateinit var viewModel:ViewModel
lateinit var recycler_Adapter:recyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setupRecyclerAdapter()
        recycler_Adapter.SetOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,bundle
            )}
    }

    private fun setupRecyclerAdapter(){
        recycler_Adapter= recyclerAdapter()
        saved_recycler_view.apply {

            this.adapter=recycler_Adapter
            this.layoutManager= LinearLayoutManager(activity)
        }
    }
}