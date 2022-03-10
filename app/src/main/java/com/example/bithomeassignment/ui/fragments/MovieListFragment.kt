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
import com.example.bithomeassignment.R
import com.example.bithomeassignment.adapters.MovieListAdapter
import com.example.bithomeassignment.databinding.FragmentMovieListBinding
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.network.Constants
import com.example.bithomeassignment.utils.LoggerUtils
import com.example.bithomeassignment.view_model.MovieListViewModel
import com.getbase.floatingactionbutton.FloatingActionButton
import com.getbase.floatingactionbutton.FloatingActionsMenu

class MovieListFragment : BaseFragment(), MovieListAdapter.OnItemClicked, View.OnClickListener {


    private val TAG = this::class.java.simpleName.toString()
    private lateinit var _binding: FragmentMovieListBinding
    private lateinit var _recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var _moviesAdapter: MovieListAdapter
    private lateinit var _extendedFab: FloatingActionsMenu
    private lateinit var _topRatedFab: FloatingActionButton
    private lateinit var _nowPlayingFab: FloatingActionButton
    private lateinit var _upcomingFab: FloatingActionButton
    private lateinit var _favoritesFab: FloatingActionButton
    private lateinit var _progressBar: ProgressBar
    var _hasInternet: Boolean = false
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
        _extendedFab = _binding.extendedFab
        _topRatedFab = _binding.topRated
        _nowPlayingFab = _binding.nowPlaying
        _upcomingFab = _binding.upcoming
        _favoritesFab = _binding.favorites
        _progressBar = _binding.progressBar
    }

    override fun onFragmentReady() {
        recyclerViewFirstAnim()
        _currentEndPoint = Constants.LATEST
        observeLoadingProgress()
    }

    // Observer to identify if there is internet connection
    override fun observeNetworkState() {
        mainActivity.getMovieListViewModel().getNetworkState().observe(this) { hasInternet ->
            if (hasInternet) {
                observeMovieList(_currentEndPoint)
                _hasInternet = true
                LoggerUtils.shortToast(requireContext(), hasInternet.toString())
            } else {
                _hasInternet = false
                LoggerUtils.shortToast(requireContext(), hasInternet.toString())
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
        _moviesAdapter = MovieListAdapter(mainActivity, movies, this)
        _recyclerView.adapter = _moviesAdapter
    }

    // Listener to check if the user reached the end of the recycler -> triggers the paging
    private fun observeRecyclerScrolling() {
        _recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 2 && _hasInternet) {
                    mainActivity.getMovieListViewModel().nextPage(_currentEndPoint, layoutManager)
                }
            }
        })
    }

    override fun initClicks() {
        _extendedFab.setOnClickListener(this)
        _topRatedFab.setOnClickListener(this)
        _nowPlayingFab.setOnClickListener(this)
        _upcomingFab.setOnClickListener(this)
        _favoritesFab.setOnClickListener(this)
    }

    override fun onMovieClicked(movie: Movie) {
        val b = Bundle()
        b.putString("movie_overview", movie.overview)
        mainActivity.addFragment(R.id.action_movieListFragment_to_movieDetailsFragment, b)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.extended_fab -> {
                _extendedFab.expand()
            }
            R.id.top_rated -> {
                if (!_hasInternet) return
                getMoviesOnClick(Constants.TOP_RATED)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
            }
            R.id.now_playing -> {
                if (!_hasInternet) return
                getMoviesOnClick(Constants.NOW_PLAYING)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
            }
            R.id.upcoming -> {
                if (!_hasInternet) return
                getMoviesOnClick(Constants.UPCOMING)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
            }
            R.id.favorites -> {
                if (!_hasInternet) return
                LoggerUtils.shortToast(requireContext(), "Favorites -> Todo")
                _extendedFab.collapse()
            }
        }
    }

    private fun getMoviesOnClick(currentEndPoint: String) {
        _extendedFab.collapse()
        _currentEndPoint = currentEndPoint
    }
}