package com.example.madlevel6task2.repository

import android.util.Log
import com.example.madlevel6task2.api.Api
import com.example.madlevel6task2.api.ApiService
import com.example.madlevel6task2.api.util.Resource
import com.example.madlevel6task2.datamodel.Movie
import com.example.madlevel6task2.datamodel.MovieSearchResult
import kotlinx.coroutines.withTimeout

class MovieRepository {
    private val movieApiService: ApiService = Api.client

    suspend fun searchMovies(apiKey: String, query: String): Resource<MovieSearchResult> {
        val response = try {
            withTimeout(5_000) {
                movieApiService.searchMovies(apiKey, query)
            }
        } catch(e: Exception) {
            Log.e("MovieRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }

        return Resource.Success(response)
    }

    suspend fun getMovieDetails(apiKey: String, movieId: Int): Resource<Movie> {
        val response = try {
            withTimeout(5_000) {
                movieApiService.getMovieDetails(movieId, apiKey)
            }
        } catch(e: Exception) {
            Log.e("MovieRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }

        return Resource.Success(response)
    }
}
