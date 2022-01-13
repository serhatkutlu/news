package com.msk.news.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Result(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    val content: Any,
    val creator: List<String>,
    val description: String,
    val full_description: String,
    val image_url: String,
    val keywords: List<String>,
    val link: String,
    val pubDate: String,
    val source_id: String,
    val title: String,
    val video_url: Any
)