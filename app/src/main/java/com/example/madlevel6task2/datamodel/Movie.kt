package com.example.madlevel6task2.datamodel

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val title: String,
    val overview: String
)