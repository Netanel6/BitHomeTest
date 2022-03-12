package com.example.bithomeassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bithomeassignment.BaseFragment
import com.example.bithomeassignment.MainActivity
import com.example.bithomeassignment.R
import com.example.bithomeassignment.adapters.MovieListAdapter
import com.example.bithomeassignment.databinding.FragmentMovieListBinding
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.network.Constants
import com.example.bithomeassignment.utils.AppUtils
import com.example.bithomeassignment.utils.LoggerUtils
import com.example.bithomeassignment.view_model.MovieListViewModel

class MovieListFragment : BaseFragment(), MovieListAdapter.OnItemClicked,
    MainActivity.OnNavItemSelected {


    private val TAG = this::class.java.simpleName.toString()
    private lateinit var _binding: FragmentMovieListBinding
    private lateinit var _recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var _moviesAdapter: MovieListAdapter
    private lateinit var _progressBar: ProgressBar
    var _hasInternet: Boolean? = null
    var inFavorites: Boolean = false
    lateinit var _currentEndPoint: String
    private val _movieListViewModel: MovieListViewModel
        get() {
            return mainActivity.getMovieListViewModel()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return _binding.root
    }

    override fun initViews() {
        _recyclerView = _binding.movieListRv
        layoutManager = LinearLayoutManager(requireContext())
        _recyclerView.layoutManager = layoutManager
        _progressBar = _binding.progressBar
    }

    override fun onFragmentReady() {
        mainActivity.setOnNavItemSelected(this)
        recyclerViewFirstAnim()
        _currentEndPoint = Constants.LATEST
        observeLoadingProgress()
        observeMovieListFromDb()
    }

    private fun observeMovieListFromDb() {

    }

    // Observer to identify if there is internet connection
    override fun observeNetworkState() {
        _movieListViewModel.getNetworkState().observe(this) { hasInternet ->
            _hasInternet = if (hasInternet) {
                observeMovieList(_currentEndPoint)
                true
            } else {
                LoggerUtils.snackBarError(_binding.fragment,getString(R.string.no_internet_connection))
                false
            }
        }
    }

    private fun recyclerViewFirstAnim() {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 800
        _recyclerView.startAnimation(anim)
    }

    private fun observeLoadingProgress() {
        _movieListViewModel._loading.observe(this) { inProgress ->
            if (inProgress) {
                _progressBar.visibility = View.VISIBLE
            } else {
                _progressBar.visibility = View.GONE
            }
        }
    }

    private fun observeMovieList(endPoint: String) {
        _movieListViewModel.getAllMoviesFromServer(endPoint, 1)
        _movieListViewModel._movieList.observe(this) { movieList ->
            if (movieList != null) {
                initRecycler(movieList)
                LoggerUtils.info(TAG, movieList.toString())
                observeRecyclerScrolling()
            }
        }
    }

    private fun initRecycler(movies: List<Movie>) {
        _moviesAdapter = MovieListAdapter(mainActivity, movies, this,inFavorites)
        _recyclerView.adapter = _moviesAdapter
    }

    // Listener to check if the user reached the end of the recycler -> triggers the paging
    private fun observeRecyclerScrolling() {
        _recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 2 && _hasInternet!!  && !inFavorites) {
                    mainActivity.getMovieListViewModel().nextPage(_currentEndPoint, layoutManager)
                }
            }
        })
    }

    override fun initClicks() {
    }

    override fun onMovieClicked(movie: Movie) {
        _movieListViewModel.setSelectedMovie(movie)
        mainActivity.hideBottomView()
        mainActivity.addFragment(R.id.action_movieListFragment_to_movieDetailsFragment, null)
    }

    override fun onFavoriteClicked(movie: Movie) {
        LoggerUtils.snackBar(_binding.fragment,"${movie.title} Saved")
        _movieListViewModel.addMovieToLocalDb(movie)
    }


    private fun getMoviesOnClick(currentEndPoint: String) {
        _currentEndPoint = currentEndPoint
    }

    override fun screenNum(screenNum: Int) {
        when (screenNum) {
            1 -> {
                if (!_hasInternet!!) return
                inFavorites = false
                getMoviesOnClick(Constants.LATEST)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
            }
            2 -> {
                if (!_hasInternet!!) return
                inFavorites = false
                getMoviesOnClick(Constants.TOP_RATED)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
            }
            3 -> {
                if (!_hasInternet!!) return
                inFavorites = false
                getMoviesOnClick(Constants.NOW_PLAYING)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
            }
            4 -> {
                if (!_hasInternet!!) return
                inFavorites = false
                getMoviesOnClick(Constants.UPCOMING)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
            }
            5 -> {
                _movieListViewModel.getDataFromLocalDb()
                inFavorites = true
            }
        }
    }
}