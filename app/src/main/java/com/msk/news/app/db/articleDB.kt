package com.msk.news.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msk.news.app.data.Article

@Database(
    entities =[Article::class],
    version = 1 )
@TypeConverters(converter::class)
abstract class articleDB:RoomDatabase() {

    abstract fun getArticleDao():ArticleDao

    companion object{

        @Volatile
        private var instance:articleDB?=null

        private val LOCK=Any()

        operator fun invoke(context:Context) =instance ?:synchronized(LOCK){
            instance?: createDatabase(context).also{
                instance=it
            }

        }
        private fun createDatabase(context: Context):articleDB{
            return Room.databaseBuilder(context.applicationContext,
            articleDB::class.java,
            "article_db_db").build()
        }




    }

}