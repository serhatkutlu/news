package com.msk.news.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msk.news.R
import com.msk.news.app.data.Result
import kotlinx.android.synthetic.main.item_article_preview.view.*


class recyclerAdapter: RecyclerView.Adapter<recyclerAdapter.ArticleHolder>() {
    inner class ArticleHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    private val differCallback=object :DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return  oldItem.image_url==newItem.image_url
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.item_article_preview,parent,false)
        return ArticleHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val article=differ.currentList.get(position)
        holder.itemView.apply {
            Glide.with(this).load(article.image_url).into(ivArticleImage)
            tvSource.text=article.source_id
            tvTitle.text=article.title
            tvDescription.text=article.description
            tvPublishedAt.text=article.pubDate
            setOnItemClickListener {
                onItemClickListener?.let{
                    (article)
                }
            }
        }
    }

    override fun getItemCount(): Int {
       return  differ.currentList.size
    }

    private var onItemClickListener:((Result)->Unit)?=null

    fun setOnItemClickListener(listener:(Result)->Unit){
        onItemClickListener=listener

    }

}