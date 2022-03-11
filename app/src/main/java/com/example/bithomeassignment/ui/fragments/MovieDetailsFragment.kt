package com.example.bithomeassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bithomeassignment.BaseFragment
import com.example.bithomeassignment.MainActivity
import com.example.bithomeassignment.R
import com.example.bithomeassignment.databinding.FragmentMovieDetailsBinding
import com.example.bithomeassignment.network.Constants
import com.example.bithomeassignment.utils.AppUtils
import com.example.bithomeassignment.view_model.MovieListViewModel

class MovieDetailsFragment : BaseFragment(), MainActivity.OnNavItemSelected {

    private val TAG = this::class.java.simpleName.toString()
    private lateinit var _binding: FragmentMovieDetailsBinding
    private lateinit var _movieName: TextView
    private lateinit var _movieDesc: TextView
    private lateinit var _movieYear: TextView
    private lateinit var _moviePhoto: ImageView
    private lateinit var _movieRating: TextView

    private val _movieListViewModel: MovieListViewModel
        get() {
            return mainActivity.getMovieListViewModel()
        }
    lateinit var _currentEndPoint: String
    var _hasInternet: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun observeNetworkState() {
        _movieListViewModel.getNetworkState().observe(this) { hasInternet ->
            _hasInternet = hasInternet
        }
    }

    override fun onFragmentReady() {
        mainActivity.setOnNavItemSelected(this)
        observeMovieDetails()

    }

    private fun observeMovieDetails() {
        _movieListViewModel.getSelectedMovie().observe(this) { movie ->
            if (movie != null) {
                _movieName.text = movie.title
                _movieDesc.text = movie.overview
                _movieYear.text = movie.releaseDate
                _movieRating.text = AppUtils.formatString(data = movie.voteAverage.toString())
               AppUtils.loadImage(activity = mainActivity, imagePath = "${Constants.IMAGE_PATH}${movie.backdropPath}",holder = _moviePhoto)

            }

        }
    }

    override fun initViews() {
        _movieName = _binding.movieName
        _movieRating = _binding.movieRating
        _movieYear = _binding.movieYear
        _movieDesc = _binding.movieDesc
        _moviePhoto = _binding.moviePhoto
    }

    override fun initClicks() {
    }

    private fun getMoviesOnClick(currentEndPoint: String) {
        _currentEndPoint = currentEndPoint
    }

    override fun screenNum(screenNum: Int) {
        when (screenNum) {
            1 -> {
                if (!_hasInternet) return
                getMoviesOnClick(Constants.LATEST)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
                mainActivity.addFragment(R.id.action_movieDetailsFragment_to_movieListFragment,
                    null)

            }
            2 -> {
                if (!_hasInternet) return
                getMoviesOnClick(Constants.TOP_RATED)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
                mainActivity.addFragment(R.id.action_movieDetailsFragment_to_movieListFragment,
                    null)
            }
            3 -> {
                if (!_hasInternet) return
                getMoviesOnClick(Constants.NOW_PLAYING)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
                mainActivity.addFragment(R.id.action_movieDetailsFragment_to_movieListFragment,
                    null)

            }
            4 -> {
                if (!_hasInternet) return
                getMoviesOnClick(Constants.UPCOMING)
                _movieListViewModel.getAllMoviesFromServer(_currentEndPoint, 1)
                mainActivity.addFragment(R.id.action_movieDetailsFragment_to_movieListFragment,
                    null)

            }
            5 -> {
//                _movieListViewModel.fromLocalDb("", 1)
            }
        }
    }


}