package com.example.themoviecatalog.database.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviecatalog.database.room.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //onConflict es la estrategia que se usara en caso de que se encuentren registros repetidos (en este caso lo va a sobreescribir)
    fun addMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies_info ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<MovieEntity>>

}