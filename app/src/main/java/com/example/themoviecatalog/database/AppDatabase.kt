package com.example.themoviecatalog.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themoviecatalog.database.room.daos.MovieDao
import com.example.themoviecatalog.database.room.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}