package com.example.bithomeassignment.repository.local_db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Created by Netanel Amar on 11/03/2022.
 * NetanelCA2@gmail.com
 */
class Converter {

    @TypeConverter
    fun listFromInt(value: String?): List<Int?>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun intFromList(list: List<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}