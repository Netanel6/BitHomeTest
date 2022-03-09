package com.example.bithomeassignment.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.repository.IDataRepository
import com.example.bithomeassignment.repository.ISettingsRepository
import com.example.bithomeassignment.utils.LoggerUtils
import kotlinx.coroutines.launch

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//app viewModel
class MovieListViewModel(var dataRepository: IDataRepository, _settingsRepository: ISettingsRepository) :
    ViewModel() {
    private val TAG = this::class.simpleName.toString()
    private var settingsRepository:ISettingsRepository = _settingsRepository
    var _movieList = MutableLiveData<List<Movie>>()

    //Method to retrieve movies from Server
    fun getAllMoviesFromServer(pageNum:Int) {
        viewModelScope.launch {
            _movieList.value = dataRepository.getAllMoviesFromServer(pageNum).movies
        }
        LoggerUtils.info(TAG,"getMoviesByPopularityFromServer")

    }

    //Method to retrieve movies from Server by top rated
    fun getMoviesByTopRatedFromServer(pageNum:Int) {
        viewModelScope.launch {
            _movieList.value = dataRepository.getMoviesByTopRatedFromServer(pageNum).movies
        }
        LoggerUtils.info(TAG,"getMoviesByPopularityFromServer")

    }

    //Method to retrieve movies from Server by upcoming
    fun getMoviesByUpcomingFromServer(pageNum:Int) {
        viewModelScope.launch {
            _movieList.value = dataRepository.getMoviesByUpcomingFromServer(pageNum).movies
        }
        LoggerUtils.info(TAG,"getMoviesByUpcomingFromServer")

    }

    //Method to retrieve data from Server
    fun getMoviesByNowPlayingFromServer(startReleaseDate:String, endReleaseDate:String) {
        viewModelScope.launch {
            // TODO: Change to start date and end date dynamically
            _movieList.value = dataRepository.getMoviesByNowPlayingFromServer(startReleaseDate,endReleaseDate).movies
        }
        LoggerUtils.info(TAG,"getMoviesByNowPlayingFromServer")

    }

}