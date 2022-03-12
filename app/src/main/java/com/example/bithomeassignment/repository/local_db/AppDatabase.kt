package com.example.bithomeassignment.repository.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bithomeassignment.models.Movie

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
*/
//app db singleton which called in data repository in the constructor and held from there
@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    private val TAG = this::class.java.simpleName

    abstract fun getMovieDao() : DataDao

    companion object{
        private var _instance: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            if (_instance == null) {
                _instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app-database").fallbackToDestructiveMigration().build()
            }
            return _instance
        }
    }
}
