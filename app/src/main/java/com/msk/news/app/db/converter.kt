package com.msk.news.app.db

import androidx.room.TypeConverter
import com.msk.news.app.data.Source

class converter {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}