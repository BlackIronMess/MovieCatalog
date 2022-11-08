package com.example.themoviecatalog.database.room.repositories

import androidx.lifecycle.LiveData
import com.example.themoviecatalog.database.AppDatabase
import com.example.themoviecatalog.database.room.entities.MovieEntity

class MovieRepository ( private val appDatabase: AppDatabase) {

    val getAllMovies: LiveData<List<MovieEntity>> = appDatabase.movieDao().getAllMovies()

    fun addMovie(movieEntity : MovieEntity){
        appDatabase.movieDao().addMovie(movieEntity)
    }

}