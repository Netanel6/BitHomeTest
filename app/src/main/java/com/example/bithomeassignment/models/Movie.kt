package com.example.bithomeassignment.models


import androidx.room.*
import com.example.bithomeassignment.repository.local_db.Converter
import com.google.gson.annotations.SerializedName

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
@Entity(tableName = "movie")
data class Movie(
    @ColumnInfo("adult")
    @SerializedName("adult")
    val adult: Boolean,
    @ColumnInfo("backdrop_path")
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @TypeConverters(Converter::class)
    @ColumnInfo("genre_ids")
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @PrimaryKey
    @ColumnInfo("id")
    @SerializedName("id")
    val id: Int,
    @ColumnInfo("original_language")
    @SerializedName("original_language")
    val originalLanguage: String,
    @ColumnInfo("original_title")
    @SerializedName("original_title")
    val originalTitle: String,
    @ColumnInfo("overview")
    @SerializedName("overview")
    val overview: String,
    @ColumnInfo("popularity")
    @SerializedName("popularity")
    val popularity: Double,
    @ColumnInfo("poster_path")
    @SerializedName("poster_path")
    val posterPath: String,
    @ColumnInfo("release_date")
    @SerializedName("release_date")
    val releaseDate: String,
    @ColumnInfo("title")
    @SerializedName("title")
    val title: String,
    @ColumnInfo("video")
    @SerializedName("video")
    val video: Boolean,
    @ColumnInfo("vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double,
    @ColumnInfo("vote_count")
    @SerializedName("vote_count")
    val voteCount: Int,
)