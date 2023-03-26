package com.example.madlevel6task2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madlevel6task2.api.util.Resource
import com.example.madlevel6task2.datamodel.Movie
import com.example.madlevel6task2.datamodel.MovieSearchResult
import com.example.madlevel6task2.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository()

    var selectedMovie: Movie? = null
        private set

    val searchResults: MutableLiveData<Resource<MovieSearchResult>>
        get() = _searchResultsResource

    private val _searchResultsResource: MutableLiveData<Resource<MovieSearchResult>> =
        MutableLiveData(
            Resource.Empty()
        )

    fun searchMovies(apiKey: String, query: String) {
        _searchResultsResource.value = Resource.Loading()

        viewModelScope.launch {
            _searchResultsResource.value = movieRepository.searchMovies(apiKey, query)
        }
    }

    fun setSelectedMovie(movie: Movie) {
        selectedMovie = movie
    }
}