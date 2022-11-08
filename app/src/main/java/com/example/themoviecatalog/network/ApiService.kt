package com.example.themoviecatalog.network

import com.example.themoviecatalog.network.responses.MovieDiscoverResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie?&sort_by=popularity.desc&include_adult=false")
    suspend fun discoverMovies (
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: String
    ) : Response<MovieDiscoverResponse>

}