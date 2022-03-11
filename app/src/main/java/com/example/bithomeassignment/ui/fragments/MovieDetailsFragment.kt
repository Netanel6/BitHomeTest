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
import com.example.bithomeassignment.utils.LoggerUtils
import com.example.bithomeassignment.view_model.MovieListViewModel

class MovieDetailsFragment : BaseFragment(){
    private val TAG = this::class.java.simpleName.toString()
    private lateinit var _binding: FragmentMovieDetailsBinding
    private lateinit var _movieName: TextView
    private lateinit var _movieDesc: TextView
    private lateinit var _movieYear: TextView
    private lateinit var _moviePhoto: ImageView
    private lateinit var _movieRating: TextView
    private var _hasInternet: Boolean? = null
    private val _movieListViewModel: MovieListViewModel
        get() {
            return mainActivity.getMovieListViewModel()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun observeNetworkState() {
        _movieListViewModel.getNetworkState().observe(this) { hasInternet ->
            _hasInternet = if (hasInternet) {
                true
            } else {
                LoggerUtils.snackBarError(_binding.root,getString(R.string.no_internet_connection))
                false
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

    override fun onFragmentReady() {
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

    override fun initClicks() {
    }
}