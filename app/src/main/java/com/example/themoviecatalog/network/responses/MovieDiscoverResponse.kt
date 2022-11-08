package com.example.themoviecatalog.network.responses

import com.example.themoviecatalog.network.entities.MovieNetworkEntity
import com.google.gson.annotations.SerializedName

class MovieDiscoverResponse (
    @SerializedName("results") var movieList: List<MovieNetworkEntity>
    )