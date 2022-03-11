package com.example.bithomeassignment.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.repository.IDataRepository
import com.example.bithomeassignment.repository.ISettingsRepository
import com.example.bithomeassignment.utils.LoggerUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//app viewModel
class MovieListViewModel(_dataRepository: IDataRepository, _settingsRepository: ISettingsRepository) :
    ViewModel() {
    private val TAG = this::class.simpleName.toString()
    private var dataRepository: IDataRepository = _dataRepository
    private val settingsRepository: ISettingsRepository = _settingsRepository
    private var _movieListScrollPosition = 0
    val _loading = MutableLiveData(false)
    private val _hasInternet = MutableLiveData(false)
    private val _pageNum = MutableLiveData(1)
    var _movieList = MutableLiveData<List<Movie>>()
    val _movie = MutableLiveData<Movie>()

    // Sets the network state of the internet
    fun setNetworkState(hasInternet: Boolean) {
        this._hasInternet.value = hasInternet
    }

    // Gets the network state of the internet
    fun getNetworkState(): MutableLiveData<Boolean> {
        return _hasInternet
    }

    //Method to retrieve movies from Server
    fun getAllMoviesFromServer(endPoint: String, pageNum: Int) {
        viewModelScope.launch {
            _movieList.value = dataRepository.getAllMoviesFromServer(endPoint, pageNum).movies
        }
        LoggerUtils.info(TAG, "getAllMoviesFromServer")
    }

    // Gets the next page of the current endPoint
    fun nextPage(currentEndPoint: String, layoutManager: LinearLayoutManager) {
        viewModelScope.launch {
            if ((_movieListScrollPosition + 1) >= _pageNum.value!!) {
                _loading.value = true
                incrementPage()
                LoggerUtils.info(TAG, "nextPage: triggered: ${_pageNum.value}")

                if (_pageNum.value!! > 1) {
                    val result = dataRepository.getAllMoviesFromServer(currentEndPoint,_pageNum.value!!)
                    LoggerUtils.info(TAG, "movies: appending")
                    appendMovies(result.movies, layoutManager)
                }
                _loading.value = false
            }
        }
    }

   // Add new movies to the list when the user reaches the end of the recycler view
    private fun appendMovies(movies: List<Movie>, linearLayoutManager: LinearLayoutManager) {
       val current = ArrayList(_movieList.value!!)
        current.addAll(movies)
       this._movieList.value = current
       _movieListScrollPosition += 1
       linearLayoutManager.scrollToPosition(current.size - 24)
   }

    // Increment the current page by 1
    private fun incrementPage() {
        _pageNum.value = _pageNum.value?.plus(1)
    }

    fun addMovieToLocalDb(movie: Movie){
        // Using IO dispatcher to prevent from UI locking and crashing the app or ANR
        // ANR -> Application Not Responding
        CoroutineScope(IO).launch {
            dataRepository.addMovieToLocaldb(movie)
        }
    }

    // Sets the selected movie for details fragment
     fun setSelectedMovie(movie: Movie) {
        this._movie.value = movie
    }

    // Gets the selected movie for details fragment
    fun getSelectedMovie():MutableLiveData<Movie> {
        return _movie
    }
}