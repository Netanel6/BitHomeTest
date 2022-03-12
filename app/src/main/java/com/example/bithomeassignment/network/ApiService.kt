package com.example.bithomeassignment.network

import com.example.bithomeassignment.models.MovieList
import com.example.bithomeassignment.models.Trailer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//interface to implement in Network manager to make the server call and retrieve the data
interface ApiService {

    //@Url is for adding part of string into the url
    //@Path is for adding part of string after the url using encoded attr
    //@Query is for adding part of string to the query after the url is added

    /**
     * Function to get all movies
     * */
    @GET("")
    suspend fun getAllMovies(
        @Url endPoint:String,
        @Query("api_key", encoded = false) apiKey: String,
        @Query("page", encoded = false) pageNum: Int,
    ): MovieList

  /**
     * Function to get trailer by movie id
     * */
    @GET("movie/{movie_id}/videos?")
    suspend fun getTrailer(
        @Path("movie_id") movieId:String,
        @Query("api_key", encoded = false) apiKey: String,
    ): Trailer

}