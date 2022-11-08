package com.example.themoviecatalog.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_info")
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var uID: String,
    var language: String,
    var title: String,
    var overview: String,
    var voteAverage: Double,
    var voteCount: Int,
    var releaseDate: String,
    var posterPath: String,
    )