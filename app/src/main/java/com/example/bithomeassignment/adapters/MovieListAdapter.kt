package com.example.bithomeassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bithomeassignment.MainActivity
import com.example.bithomeassignment.databinding.MovieItemSingleCellBinding
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.network.Constants
import com.google.android.material.textview.MaterialTextView


/**

 * Created by Netanel Amar on 08/03/2022.
 * NetanelCA2@gmail.com
 */
// TODO: Add lazy loading!
class MovieListAdapter(
    private val activity:MainActivity,
    private val values: List<Movie>,
    private val onItemClicked: OnItemClicked,
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    val TAG = this::class.java.simpleName.toString()
    var _context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _context = parent.context
        return ViewHolder(MovieItemSingleCellBinding.inflate(LayoutInflater.from(_context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataAtPosition = values[position]
        holder.movieName.text = dataAtPosition.title
        holder.itemView.setOnClickListener {
            onItemClicked.onMovieClicked(dataAtPosition)
        }
        Glide.with(activity).load("${Constants.IMAGE_PATH}${dataAtPosition.posterPath}").into(holder.posterPath)
        holder.voteAvg.text = String.format("%s/10", dataAtPosition.voteAverage.toString())

    }

    override fun getItemCount(): Int {
        return values.size
    }

    class ViewHolder(binding: MovieItemSingleCellBinding) : RecyclerView.ViewHolder(binding.root) {
        val movieName: MaterialTextView = binding.movieName
        val posterPath: ImageView = binding.posterPath
        val voteAvg: MaterialTextView = binding.avgVote
    }

    interface OnItemClicked {
        fun onMovieClicked(movie:Movie)
    }
}


/**/