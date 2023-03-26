package com.example.madlevel6task2.ui.screens

import androidx.annotation.StringRes
import com.example.madlevel6task2.R

sealed class MovieScreens(
    val route: String,
    @StringRes val labelResourceId: Int
) {
    object MovieListScreen : MovieScreens("movieList", R.string.movie_list)
    object MovieDetailScreen : MovieScreens("movieDetail", R.string.movie_detail)
}

