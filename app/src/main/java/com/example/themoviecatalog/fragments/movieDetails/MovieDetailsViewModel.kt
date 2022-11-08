package com.example.themoviecatalog.fragments.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.themoviecatalog.R
import com.example.themoviecatalog.database.preferences.AppPreferences
import com.example.themoviecatalog.database.room.entities.MovieEntity
import com.example.themoviecatalog.database.room.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MovieDetailsViewModel @Inject constructor (movieRepository: MovieRepository, private val appPreferences: AppPreferences) : ViewModel() {

    val getAllMovies: LiveData<List<MovieEntity>> = movieRepository.getAllMovies

    fun loadSelectedMovieDetails ( localMovieList : ArrayList<MovieEntity> ) : MovieEntity {
        lateinit var selectedMovie : MovieEntity
        for (currentMovie in localMovieList){
            if (currentMovie.title == appPreferences.getSelectedMovie()){
                selectedMovie = MovieEntity(id = 0, uID = currentMovie.uID, language = currentMovie.language,
                    title = currentMovie.title, overview = currentMovie.overview, voteAverage = currentMovie.voteAverage, voteCount = currentMovie.voteCount,
                    releaseDate = currentMovie.releaseDate, posterPath = currentMovie.posterPath)
                break
            }
        }
        return selectedMovie
    }
    fun movieInFavoritesList (movieTitle: String) : Boolean {
        val movieInList : Boolean
        val userFavoritesList : ArrayList<String> = arrayListOf()
        userFavoritesList.addAll(appPreferences.getUserFavoritesList()!!.toList())
        movieInList = userFavoritesList.contains(movieTitle)
        return movieInList
    }

    fun controlUserFavoritesList(movieTitle : String): Boolean {
        val movieAdded : Boolean
        val userFavoritesList : ArrayList<String> = arrayListOf()
        userFavoritesList.addAll(appPreferences.getUserFavoritesList()!!.toList())

        movieAdded = if ( userFavoritesList.contains(movieTitle) ){ //Pelicula se encuentra en favoritos
            userFavoritesList.remove(movieTitle)
            appPreferences.setUserFavoritesList(userFavoritesList.toMutableSet())
            false
        } else { //Pelicula no e encuentra en favoritos
            userFavoritesList.add(movieTitle)
            appPreferences.setUserFavoritesList(userFavoritesList.toMutableSet())
            true
        }

        return movieAdded
    }

    fun getCorrectGradeImage ( movieEntity: MovieEntity ) : Int{
        val selectedImage = when ( movieEntity.voteAverage.roundToInt() ) {
            1 -> { R.drawable.img_stars_1 }
            2 -> { R.drawable.img_stars_1 }
            3 -> { R.drawable.img_stars_2 }
            4 -> { R.drawable.img_stars_2 }
            5 -> { R.drawable.img_stars_3 }
            6 -> { R.drawable.img_stars_3 }
            7 -> { R.drawable.img_stars_4 }
            8 -> { R.drawable.img_stars_4 }
            9 -> { R.drawable.img_stars_4 }
            10 -> { R.drawable.img_stars_5 }
            else -> { R.drawable.img_stars_5 }
        }
        return selectedImage
    }

}