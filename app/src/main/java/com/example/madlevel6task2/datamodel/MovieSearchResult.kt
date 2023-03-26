package com.example.madlevel6task2.datamodel

import com.google.gson.annotations.SerializedName

data class MovieSearchResult(
    val results: List<Movie>,
    @SerializedName("total_results") val totalResults: Int
)

