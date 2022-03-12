package com.example.bithomeassignment.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.bithomeassignment.BaseFragment
import com.example.bithomeassignment.R
import com.example.bithomeassignment.databinding.FragmentMovieDetailsBinding
import com.example.bithomeassignment.network.Constants
import com.example.bithomeassignment.utils.AppUtils
import com.example.bithomeassignment.utils.LoggerUtils
import com.example.bithomeassignment.view_model.MovieListViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class MovieDetailsFragment : BaseFragment(), View.OnClickListener {
    private val TAG = this::class.java.simpleName.toString()
    private lateinit var _binding: FragmentMovieDetailsBinding
    private lateinit var _movieName: MaterialTextView
    private lateinit var _movieDesc: MaterialTextView
    private lateinit var _movieYear: MaterialTextView
    private lateinit var _moviePhoto: ImageView
    private lateinit var _movieRating: MaterialTextView
    private lateinit var _playerView: YouTubePlayerView
    private lateinit var _openInBrowser: MaterialButton
    private var _hasInternet: Boolean? = null
    private var _trailer: String? = null
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
        _playerView = _binding.playerView
        _openInBrowser = _binding.openInBrowser
    }

    override fun onFragmentReady() {
        lifecycle.addObserver(_playerView);
        _playerView.enableAutomaticInitialization
        observeMovieDetails()
        observeTrailer()
    }

    private fun observeTrailer() {
        _movieListViewModel._trailer.observe(this) {
            if (it != null) {
                _trailer = it.results[0].key
                _playerView.visibility = View.VISIBLE
                _playerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(_trailer!!, 0f)
                        _openInBrowser.isEnabled = true
                    }
                })
                _movieListViewModel._trailer.value = null
            }
        }
    }


    private fun observeMovieDetails() {
        _movieListViewModel.getSelectedMovie().observe(this) { movie ->
            if (movie != null) {
                LoggerUtils.info(TAG, movie.toString())
                _movieName.text = movie.title
                _movieDesc.text = movie.overview
                _movieYear.text = movie.releaseDate
                _movieRating.text = AppUtils.formatString(data = movie.voteAverage.toString())
                AppUtils.loadImage(activity = mainActivity,
                    imagePath = "${Constants.IMAGE_PATH}${movie.backdropPath}",
                    holder = _moviePhoto)
                _movieListViewModel.getTrailer(movie.id.toString())
            }
        }
    }

    override fun initClicks() {
        _openInBrowser.setOnClickListener(this)
    }

    private fun openTrailer() {
        try {
            val openTrailerIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("${Constants.YOUTUBE_TRAILER_PATH}$_trailer"))
            startActivity(openTrailerIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.open_in_browser -> {
                openTrailer()
            }
        }

    }


}