package com.example.bithomeassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bithomeassignment.MainActivity
import com.example.bithomeassignment.R
import com.example.bithomeassignment.databinding.MovieItemSingleCellBinding
import com.example.bithomeassignment.models.Movie
import com.example.bithomeassignment.network.Constants
import com.example.bithomeassignment.utils.AppUtils
import com.example.bithomeassignment.utils.LoggerUtils
import com.google.android.material.textview.MaterialTextView


/**

 * Created by Netanel Amar on 08/03/2022.
 * NetanelCA2@gmail.com
 */
class MovieListAdapter(
    private val activity:MainActivity,
    private val values: List<Movie>,
    private val onItemClicked: OnItemClicked,
    private val inFavorites:Boolean
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
        AppUtils.loadImage(activity = activity, imagePath = "${Constants.IMAGE_PATH}${dataAtPosition.posterPath}",holder = holder.posterPath)
        holder.voteAvg.text = AppUtils.formatString(data = dataAtPosition.voteAverage.toString())
        if (!inFavorites){
            holder.favorite.setOnClickListener {
                holder.favorite.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.ic_favorite))
                onItemClicked.onFavoriteClicked(dataAtPosition)
            }
            holder.itemView.setOnClickListener {
                onItemClicked.onMovieClicked(dataAtPosition)
            }
        }else{
            holder.favorite.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.ic_favorite))
        }

    }

    override fun getItemCount(): Int {
        return values.size
    }

    class ViewHolder(binding: MovieItemSingleCellBinding) : RecyclerView.ViewHolder(binding.root) {
        val movieName: MaterialTextView = binding.movieName
        val posterPath: ImageView = binding.posterPath
        val favorite: ImageView = binding.addToFavorite
        val voteAvg: MaterialTextView = binding.avgVote
    }

    interface OnItemClicked {
        fun onMovieClicked(movie:Movie)
        fun onFavoriteClicked(movie:Movie)
    }
}
