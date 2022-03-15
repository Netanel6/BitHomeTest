package com.example.bithomeassignment.repository

import NetworkManager
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.models.MovieList
import com.example.bithomeassignment.models.Trailer
import com.example.bithomeassignment.network.ApiService
import com.example.bithomeassignment.network.Constants
import com.example.bithomeassignment.repository.local_db.DataDao
import javax.inject.Inject

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//Class to handle data all over the app
class DataRepository @Inject constructor(
    private val networkManager: ApiService,
    private val dataDao: DataDao,
) {
    val TAG = this::class.java.simpleName.toString()

    // Suspend fun is added because I'm using coroutines
    suspend fun getAllMoviesFromServer(endPoint: String, pageNum: Int): MovieList {
        return networkManager.getAllMovies(endPoint, Constants.API_KEY, pageNum)
    }

    // Suspend fun is added because I'm using coroutines
    suspend fun getTrailer(movieId: String): Trailer {
        return networkManager.getTrailer(movieId,Constants.API_KEY)
    }

    // Suspend fun is added because I'm using coroutines
    suspend fun addMovieToLocaldb(movie: Movie) {
        dataDao.insert(movie)
    }

    // Suspend fun is added because I'm using coroutines
    suspend fun getMoviesFromLocalDb(): List<Movie> {
        return dataDao.getData()
    }

}