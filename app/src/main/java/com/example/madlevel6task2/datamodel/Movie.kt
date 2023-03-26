package com.example.madlevel6task2.datamodel

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    val overview: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("backdrop_path") val backdropPath: String?
)