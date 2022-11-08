package com.example.themoviecatalog.fragments.userFavorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.themoviecatalog.database.preferences.AppPreferences
import com.example.themoviecatalog.database.room.entities.MovieEntity
import com.example.themoviecatalog.database.room.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserFavoritesViewModel @Inject constructor ( movieRepository: MovieRepository, private val appPreferences: AppPreferences) : ViewModel() {

    val getAllMovies: LiveData<List<MovieEntity>> = movieRepository.getAllMovies

    fun setMovieToNavigate ( title : String ) {
        appPreferences.setSelectedMovie(title)
    }

    fun setSearchKeyword ( keyword : String ) {
        appPreferences.setSearchKeyword(keyword)
    }

    fun getFavoriteMovies ( localMovieList : ArrayList<MovieEntity> ) : ArrayList<MovieEntity> {
        val userFavoritesList : ArrayList<MovieEntity> = arrayListOf()
        for (currentMovie in localMovieList){
            if ( appPreferences.getUserFavoritesList()!!.contains(currentMovie.title) ){

                val favoriteMovie = MovieEntity(id = currentMovie.id, uID = currentMovie.uID, language = currentMovie.language,
                    title = currentMovie.title, overview = currentMovie.overview, voteAverage = currentMovie.voteAverage, voteCount = currentMovie.voteCount,
                    releaseDate = currentMovie.releaseDate, posterPath = currentMovie.posterPath)
                userFavoritesList.add(favoriteMovie)
            }
        }
        return userFavoritesList
    }

    fun getFilteredFavoriteMovies (favoriteMovies : ArrayList<MovieEntity>, keyWord : String ) : ArrayList<MovieEntity> {
        var userFilteredFavoritesList : ArrayList<MovieEntity> = arrayListOf()
        if (keyWord != ""){
            for (currentMovie in favoriteMovies){
                if ( (currentMovie.title.lowercase()).contains(keyWord.lowercase()) ){

                    val favoriteMovie = MovieEntity(id = currentMovie.id, uID = currentMovie.uID, language = currentMovie.language,
                        title = currentMovie.title, overview = currentMovie.overview, voteAverage = currentMovie.voteAverage, voteCount = currentMovie.voteCount,
                        releaseDate = currentMovie.releaseDate, posterPath = currentMovie.posterPath)
                    userFilteredFavoritesList.add(favoriteMovie)
                }
            }
        } else {
            userFilteredFavoritesList = favoriteMovies
        }
        return userFilteredFavoritesList
    }

}