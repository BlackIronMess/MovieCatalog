package com.example.themoviecatalog.network.entities

import com.google.gson.annotations.SerializedName

class MovieNetworkEntity (

    @SerializedName("id") val uID: String?,
    @SerializedName("original_language") var language: String,
    @SerializedName("title") var title: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("vote_count") var voteCount: Int,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("poster_path") var posterPath: String,

)