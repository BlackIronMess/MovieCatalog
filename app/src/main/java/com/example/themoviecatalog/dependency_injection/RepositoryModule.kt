package com.example.themoviecatalog.dependency_injection

import com.example.themoviecatalog.database.AppDatabase
import com.example.themoviecatalog.database.room.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesMovieRepository( appDatabase: AppDatabase ) : MovieRepository {
        return MovieRepository(appDatabase)
    }



}