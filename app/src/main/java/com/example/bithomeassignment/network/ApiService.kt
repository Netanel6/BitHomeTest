package com.example.bithomeassignment.network

import com.example.bithomeassignment.models.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//interface to implement in Network manager to make the server call and retrieve the data
interface ApiService {

    /**
     * Function to get all movies
     * */
    @GET("discover/movie?")
    suspend fun getAllMovies(
        @Query("api_key", encoded = false) apiKey: String,
        @Query("page", encoded = false) pageNum: Int,
    ): MovieList

    /**
     * Function to get movies by Top rated
     * */
    @GET("movie/top_rated?language=en-US")
    suspend fun getMoviesByTopRated(
        @Query("api_key", encoded = false) apiKey: String,
        @Query("page", encoded = false) pageNum: Int,
    ): MovieList

    /**
     * Function to get movies by Upcoming
     * */

    @GET("movie/upcoming?language=en-US")
    suspend fun getMoviesByUpcoming(
        @Query("api_key", encoded = false) apiKey: String,
        @Query("page", encoded = false) pageNum: Int,
    ): MovieList

    /**
     *
     * Function to get movies by Now playing
     * */
    @GET("discover/movie?language=en-US")
    suspend fun getMoviesByNowPlaying(
        @Query("api_key", encoded = false) apiKey: String,
        @Query("primary_release_date.gte", encoded = false) startReleaseDate: String,
        @Query("primary_release_date.lte", encoded = false) endReleaseDate: String,
    ): MovieList


}