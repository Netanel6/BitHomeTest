package com.example.bithomeassignment.repository

import NetworkManager
import android.content.Context
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.models.MovieList
import com.example.bithomeassignment.repository.local_db.AppDatabase
import com.example.bithomeassignment.utils.LoggerUtils

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//Class to handle data all over the app
class DataRepository(context: Context, settingsRepository: SettingsRepository, networkManager: NetworkManager) : IDataRepository {
    val TAG = this::class.java.simpleName.toString()
    private var _database: AppDatabase
    private val _settingsRepository: SettingsRepository
    private var _networkManager: NetworkManager

    init {
        LoggerUtils.info(TAG, "Initializing DataRepository..")
        _database = AppDatabase.getAppDatabase(context)!!
        _settingsRepository = settingsRepository
        _networkManager = networkManager
    }

    override suspend fun getAllMoviesFromServer(endPoint: String, pageNum: Int): MovieList {
        return _networkManager.getAllData(endPoint, pageNum)
    }

    override suspend fun addMovieToLocaldb(movie: Movie) {
        _database.getMovieDao().insert(movie)
    }

}