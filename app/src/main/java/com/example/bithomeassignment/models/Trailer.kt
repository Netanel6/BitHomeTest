package com.example.bithomeassignment.models


import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)