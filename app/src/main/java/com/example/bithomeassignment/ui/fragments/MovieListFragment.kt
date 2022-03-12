package com.example.bithomeassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
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
import com.example.bithomeassignment.utils.LoggerUtils
import com.example.bithomeassignment.view_model.MovieListViewModel

class MovieListFragment : BaseFragment(), MovieListAdapter.OnItemClicked,
    MainActivity.OnNavItemSelected {


    private val TAG = this::class.java.simpleName.toString()
    private lateinit var _binding: FragmentMovieListBinding
    private lateinit var _recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var _moviesAdapter: MovieListAdapter
    private lateinit var _nextPageProgress: ProgressBar
    private lateinit var _movieLoadingProgress: ProgressBar
    private lateinit var _noConnectionImage: ImageView
    var _hasInternet: Boolean? = null
    var inFavorites: Boolean = false
    var _currentEndPoint: String = Constants.LATEST
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
        _nextPageProgress = _binding.nextPageProgressBar
        _movieLoadingProgress = _binding.movieLoadingProgressBar
        _noConnectionImage = _binding.noConnectionImage
    }

    override fun onFragmentReady() {
        mainActivity.setOnNavItemSelected(this)
        recyclerViewFirstAnim()
        observeNextPageProgress()
        observeMovieLoadingProgress()

    }

    // Observer to identify if there is internet connection
    override fun observeNetworkState() {
        _movieListViewModel.getNetworkState().observe(this) { hasInternet ->
            if (hasInternet != null) {
                _hasInternet = if (hasInternet) {
                    observeMovieList()
                    _noConnectionImage.visibility = View.GONE
                    true
                } else {
                    _noConnectionImage.visibility = View.VISIBLE
                    false
                }
            }
        }
    }

    // RecyclerView init
    private fun initRecycler(movies: List<Movie>) {
        _moviesAdapter = MovieListAdapter(mainActivity, movies, this,inFavorites)
        _recyclerView.adapter = _moviesAdapter
    }

    // RecyclerView animation
    private fun recyclerViewFirstAnim() {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 800
        _recyclerView.startAnimation(anim)
    }

    // Observer to handle visibility of next page bottom loader
    private fun observeNextPageProgress() {
        _movieListViewModel._loading.observe(this) { inProgress ->
            if (inProgress) {
                _nextPageProgress.visibility = View.VISIBLE
            } else {
                _nextPageProgress.visibility = View.GONE
            }
        }
    }

    // Observer to handle visibility of movie list loader
    private fun observeMovieLoadingProgress() {
        _movieListViewModel._movieLoading.observe(this) { inProgress ->
            if (inProgress) {
                _movieLoadingProgress.visibility = View.VISIBLE
            } else {
                _movieLoadingProgress.visibility = View.GONE
            }
        }
    }

    // Observer to handle movie list data retrieving
    private fun observeMovieList() {
        _movieListViewModel._movieList.observe(this) { movieList ->
            if (movieList != null && movieList.isNotEmpty()) {
                initRecycler(movieList)
                LoggerUtils.info(TAG, movieList.toString())
                observeRecyclerScrolling()
            } else if (movieList.isEmpty()) {
                _binding.emptyListText.visibility = View.VISIBLE
                _recyclerView.visibility = View.GONE
            }
        }
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
        //Not in use
    }

    // Listener to recyclerView item Click
    override fun onMovieClicked(movie: Movie) {
        if (_hasInternet!!) {
            _movieListViewModel.setSelectedMovie(movie)
            mainActivity.hideBottomView()
            mainActivity.addFragment(R.id.action_movieListFragment_to_movieDetailsFragment, null)
        } else {
            LoggerUtils.snackBarError(_binding.fragment, getString(R.string.no_internet_connection))
        }
    }

    // Listener to recyclerView item add to favorite Click
    override fun onFavoriteClicked(movie: Movie) {
        LoggerUtils.snackBar(_binding.fragment,"${movie.title} Saved")
        _movieListViewModel.addMovieToLocalDb(movie)
    }

    // Function to handle BottomNavigationView click from main activity
    private fun getMoviesOnClick(currentEndPoint: String) {
        inFavorites = false
        _binding.emptyListText.visibility = View.INVISIBLE
        _recyclerView.visibility = View.VISIBLE
        _currentEndPoint = currentEndPoint
    }

    // Listener from MainActivity to handle filter
    /*
    * 1. Latest
    * 2. Top rated
    * 3. Upcoming
    * 4. Now playing
    * 5. Saved
    */
    override fun screenNum(screenNum: Int) {
        // resets the page and scroll position in the viewModel
        _movieListViewModel.onNavMenuSelected(1, 0)
        when (screenNum) {
            1 -> {
                if (_hasInternet == null || !_hasInternet!!) return
                getMoviesOnClick(Constants.LATEST)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint)
            }
            2 -> {
                if (_hasInternet == null || !_hasInternet!!) return
                getMoviesOnClick(Constants.TOP_RATED)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint)
            }
            3 -> {
                if (_hasInternet == null || !_hasInternet!!) return
                getMoviesOnClick(Constants.UPCOMING)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint)
            }
            4 -> {
                if (_hasInternet == null || !_hasInternet!!) return
                getMoviesOnClick(Constants.NOW_PLAYING)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint)
            }
            5 -> {
                if (_hasInternet == null || !_hasInternet!!) return
                _movieListViewModel.getDataFromLocalDb()
                inFavorites = true
            }
        }
    }
}