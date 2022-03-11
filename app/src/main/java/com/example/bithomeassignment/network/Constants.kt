package com.example.bithomeassignment.network

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
// constant class to hold the api base url and the api key
class Constants {
    companion object{
        // Server
        const val API_KEY = "f7ad2715006291578ee90cb5a6c64eb2"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_PATH = "https://image.tmdb.org/t/p/original/"
        const val LATEST = "discover/movie?"
        const val YOUTUBE_TRAILER_PATH = "https://www.youtube.com/watch?v="
        const val TOP_RATED = "movie/top_rated?language=en-US"
        const val UPCOMING = "movie/upcoming?language=en-US"
        const val NOW_PLAYING = "movie/now_playing?language=en-US"
    }

}