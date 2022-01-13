package com.msk.news.app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msk.news.app.data.Result
import retrofit2.http.DELETE

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article:Result):Long

    @Query("Select * From articles")
    fun getAllArticles():LiveData<List<Result>>

    @DELETE
    suspend fun deleteArticle(article: Result)


}