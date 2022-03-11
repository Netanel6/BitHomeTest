package com.example.bithomeassignment.repository

import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.models.MovieList

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//Interface that implemented in the data repository
interface IDataRepository {
    suspend fun getAllMoviesFromServer(endPoint:String, pageNum:Int):MovieList
    suspend fun addMovieToLocaldb(movie: Movie)
}