package com.example.bithomeassignment.repository

import com.example.bithomeassignment.models.MovieList

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//Interface that implemented in the data repository
interface IDataRepository {
    suspend fun getAllMoviesFromServer(pageNum:Int):MovieList
    suspend fun getMoviesByTopRatedFromServer(pageNum:Int):MovieList
    suspend fun getMoviesByUpcomingFromServer(pageNum:Int):MovieList
    suspend fun getMoviesByNowPlayingFromServer(startReleaseDate:String, endReleaseDate:String):MovieList
}