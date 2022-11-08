package com.example.themoviecatalog.fragments.home

import android.util.Log
import com.example.themoviecatalog.BuildConfig
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviecatalog.database.preferences.AppPreferences
import com.example.themoviecatalog.database.room.entities.MovieEntity
import com.example.themoviecatalog.database.room.repositories.MovieRepository
import com.example.themoviecatalog.network.ApiService
import com.example.themoviecatalog.network.entities.MovieNetworkEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor ( private val movieRepository: MovieRepository, private val appPreferences: AppPreferences, private val retrofit: Retrofit) : ViewModel() {

    val getAllMovies: LiveData<List<MovieEntity>> = movieRepository.getAllMovies

    private fun addMovie(movieEntity : MovieEntity){
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.addMovie(movieEntity)
        }
    }

    fun checkForLocalDataAvailability () {
        if (appPreferences.getLastRequestedPage() == 0){
            getMoviesFromApi()
        }
    }

    fun getMoviesFromApi ( ) {
        viewModelScope.launch(Dispatchers.IO) {
            val page = appPreferences.getLastRequestedPage()!! + 1
            val result = retrofit.create(ApiService::class.java).discoverMovies(apiKey = BuildConfig.TMDB_API_KEY, language = "en-US", page = page.toString())
            val discoverInfo = result.body()
            if (result.isSuccessful){
                appPreferences.setLastRequestedPage(page)
                val movieList = discoverInfo?.movieList
                if (movieList != null) {
                    for ( currentMovie : MovieNetworkEntity in movieList ){

                        val newMovieEntity = MovieEntity(id = 0, uID = currentMovie.uID.toString(), language = currentMovie.language,
                            title = currentMovie.title, overview = currentMovie.overview, voteAverage = currentMovie.voteAverage, voteCount = currentMovie.voteCount,
                        releaseDate = currentMovie.releaseDate, posterPath = currentMovie.posterPath)
                        addMovie(newMovieEntity)
                    }
                }

            } else {
                Log.e("HomeViewModel", result.errorBody().toString())
            }

        }
    }

    fun setMovieToNavigate ( title : String ) {
        appPreferences.setSelectedMovie(title)
    }


}