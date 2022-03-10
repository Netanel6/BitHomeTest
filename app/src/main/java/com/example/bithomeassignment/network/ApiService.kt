package com.example.bithomeassignment.network

import com.example.bithomeassignment.models.MovieList
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

    /**
     * Function to get all movies
     * */
    @GET("")
    suspend fun getAllMovies(
        @Url() endPoint:String,
        @Query("api_key", encoded = false) apiKey: String,
        @Query("page", encoded = false) pageNum: Int,
    ): MovieList

}