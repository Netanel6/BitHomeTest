package com.example.bithomeassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bithomeassignment.BaseFragment
import com.example.bithomeassignment.R
import com.example.bithomeassignment.adapters.MovieListAdapter
import com.example.bithomeassignment.databinding.FragmentMovieListBinding
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.utils.AppUtils
import com.example.bithomeassignment.utils.LoggerUtils
import com.example.bithomeassignment.view_model.MovieListViewModel
import com.getbase.floatingactionbutton.FloatingActionButton
import com.getbase.floatingactionbutton.FloatingActionsMenu

class MovieListFragment : BaseFragment(), MovieListAdapter.OnItemClicked, View.OnClickListener {
    private val TAG = this::class.java.simpleName.toString()
    private lateinit var _binding: FragmentMovieListBinding
    lateinit var _extendedFab: FloatingActionsMenu
    lateinit var _topRatedFab: FloatingActionButton
    lateinit var _nowPlayingFab: FloatingActionButton
    lateinit var _upcomingFab: FloatingActionButton
    lateinit var _favoritesFab: FloatingActionButton
    private val _movieListViewModel: MovieListViewModel
        get() {
            return mainActivity.getMovieListViewModel()
        }
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _moviesAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return _binding.root
    }

    override fun onFragmentReady() {
        getMoviesFromServer(1)
    }

    private fun getMoviesFromServer(pageNum: Int) {
        _movieListViewModel.getAllMoviesFromServer(pageNum)
        _movieListViewModel._movieList.observe(this) { movieList ->
            if (movieList != null) {
                initRecycler(movieList)
                LoggerUtils.info("123321", movieList.toString())
            }
        }
    }

    override fun initViews() {
        _recyclerView = _binding.movieListRv
        val layoutManager = LinearLayoutManager(requireContext())
        _recyclerView.layoutManager = layoutManager
        _extendedFab = _binding.extendedFab
        _topRatedFab = _binding.topRated
        _nowPlayingFab = _binding.nowPlaying
        _upcomingFab = _binding.upcoming
        _favoritesFab = _binding.favorites
    }

    private fun initRecycler(movies: List<Movie>) {
        _moviesAdapter = MovieListAdapter(mainActivity, movies, this)
        _recyclerView.adapter = _moviesAdapter
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 700
        _recyclerView.startAnimation(anim)
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
                _extendedFab.collapse()
                _movieListViewModel.getMoviesByTopRatedFromServer(1)
            }
            R.id.now_playing -> {
                _extendedFab.collapse()
                val date = AppUtils.getDate()
                LoggerUtils.shortToast(requireContext(),date.toString())
                _movieListViewModel.getMoviesByNowPlayingFromServer(date.toString(), date.toString())
            }
            R.id.upcoming -> {
                _extendedFab.collapse()
                _movieListViewModel.getMoviesByUpcomingFromServer(1)
            }
            R.id.favorites -> {
                LoggerUtils.shortToast(requireContext(),"Favorites -> Todo")
                _extendedFab.collapse()
            }
        }
    }
}