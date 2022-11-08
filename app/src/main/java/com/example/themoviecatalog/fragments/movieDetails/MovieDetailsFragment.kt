package com.example.themoviecatalog.fragments.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.themoviecatalog.BuildConfig
import com.example.themoviecatalog.R
import com.example.themoviecatalog.database.preferences.AppPreferences
import com.example.themoviecatalog.database.room.entities.MovieEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var toggleAddToFavorites : ToggleButton
    private lateinit var tvMovieDetailsName : TextView
    private lateinit var tvMovieOverview : TextView
    private lateinit var tvMovieGradeDetails : TextView
    private lateinit var tvMovieDetailsLanguage : TextView
    private lateinit var ivMovieDetailsSnapshot : ImageView
    private lateinit var ivMovieGrade : ImageView
    private lateinit var moviesList : ArrayList<MovieEntity>
    private var movieAdded : Boolean = false

    @Inject
    lateinit var appPreferences: AppPreferences

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        toggleAddToFavorites = view.findViewById(R.id.toggleAddToFavorites)
        tvMovieDetailsName = view.findViewById(R.id.tvMovieDetailsName)
        tvMovieOverview = view.findViewById(R.id.tvMovieOverview)
        tvMovieGradeDetails = view.findViewById(R.id.tvMovieGradeDetails)
        tvMovieDetailsLanguage = view.findViewById(R.id.tvMovieDetailsLanguage)
        ivMovieGrade = view.findViewById(R.id.ivMovieGrade)
        ivMovieDetailsSnapshot = view.findViewById(R.id.ivMovieDetailsSnapshot)

        moviesList = arrayListOf()

        tvMovieDetailsName.setOnClickListener{
            Toast.makeText(view.context, appPreferences.getUserFavoritesList().toString(), Toast.LENGTH_SHORT).show()
        }

        movieDetailsViewModel.getAllMovies.observe( viewLifecycleOwner) {
            moviesList.clear()
            moviesList.addAll(it)
            val selectedMovie = movieDetailsViewModel.loadSelectedMovieDetails(moviesList)

            //Buscamos su la pelicula ya se ecuentra en lista de favoritos
            toggleAddToFavorites.isChecked =
                movieDetailsViewModel.movieInFavoritesList(selectedMovie.title)

            val movieSnapshot = BuildConfig.TMDB_API_IMAGES_BASE_URL + "original/${selectedMovie.posterPath}"
            Glide.with(view.context).load(movieSnapshot).fitCenter().into(ivMovieDetailsSnapshot)
            tvMovieDetailsName.text = selectedMovie.title
            tvMovieOverview.text = selectedMovie.overview
            tvMovieGradeDetails.text = view.context.getString(
                R.string.text_movie_grade_info,
                selectedMovie.voteAverage.toString(),
                selectedMovie.voteCount.toString()
            )
            ivMovieGrade.setBackgroundResource(
                movieDetailsViewModel.getCorrectGradeImage(
                    selectedMovie
                )
            )

            when ( selectedMovie.language ){
                "en" -> {tvMovieDetailsLanguage.text = getString(R.string.text_language_en)}
                "es" -> {tvMovieDetailsLanguage.text = getString(R.string.text_language_es)}
                "fr" -> {tvMovieDetailsLanguage.text = getString(R.string.text_language_fr)}
                "ja" -> {tvMovieDetailsLanguage.text = getString(R.string.text_language_ja)}
                else -> {tvMovieDetailsLanguage.text = getString(R.string.text_language_other)}
            }


            toggleAddToFavorites.setOnCheckedChangeListener { _, isChecked ->
                movieAdded = if (isChecked) { //Pelicula en lista
                    movieDetailsViewModel.controlUserFavoritesList(selectedMovie.title)
                } else { //Pelicula nueva
                    movieDetailsViewModel.controlUserFavoritesList(selectedMovie.title)
                }
                if (movieAdded) {
                    Toast.makeText(
                        view.context,
                        "Pelicula agregada a lista de favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        view.context,
                        "Pelicula eliminada a lista de favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        return view
    }

}