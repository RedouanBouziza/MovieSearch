package com.example.madlevel6task2.api

import com.example.madlevel6task2.datamodel.MovieSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://api.themoviedb.org/3/search/movie?api_key=d1ab01af45305e5d9db7124878e553a0&query=batman&page=1
    //GET search/movie
    //https://api.themoviedb.org/3/movie/550?api_key=d1ab01af45305e5d9db7124878e553a0

    @GET("/3/search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
    ): MovieSearchResult

}