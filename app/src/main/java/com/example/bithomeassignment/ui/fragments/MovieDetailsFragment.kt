package com.example.bithomeassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bithomeassignment.BaseFragment
import com.example.bithomeassignment.R
import com.example.bithomeassignment.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : BaseFragment() {
    private val TAG = this::class.java.simpleName.toString()
    private lateinit var _binding: FragmentMovieDetailsBinding
    private lateinit var test: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onFragmentReady() {
        if (arguments != null) {
            val test2 = requireArguments().getString("movie_overview")
            test.text = test2
        }
        test.setOnClickListener {
            mainActivity.addFragment(R.id.action_movieDetailsFragment_to_movieListFragment, null)
        }
    }

    override fun initViews() {
        test = _binding.test
    }

    override fun initClicks() {
        // TODO: Init Clicks
    }


}