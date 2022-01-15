package com.msk.news.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msk.news.R
import com.msk.news.app.data.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*


class recyclerAdapter(): RecyclerView.Adapter<recyclerAdapter.ArticleHolder>() {

    class ArticleHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    private val differCallback=object:DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {

            return  oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {

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
        //val article=Liste.get(position)
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text=article.source.name
            tvTitle.text=article.title
            tvDescription.text=article.description
            tvPublishedAt.text=article.publishedAt
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

    private var onItemClickListener:((Article)->Unit)?=null

    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener=listener

    }

}