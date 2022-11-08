package com.example.themoviecatalog.fragments.userFavorites

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviecatalog.MovieCatalogApplication
import com.example.themoviecatalog.R
import com.example.themoviecatalog.adapters.MovieAdapter
import com.example.themoviecatalog.database.room.entities.MovieEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserFavoritesFragment : Fragment() {

    private lateinit var moviesList : ArrayList<MovieEntity>
    private lateinit var favoriteMoviesList : ArrayList<MovieEntity>

    private lateinit var etSearch : TextView
    private lateinit var llSearchContainer : LinearLayout
    private lateinit var ivSearch : ImageView
    private lateinit var recyclerFavoriteMovies : RecyclerView

    @Inject
    lateinit var application: MovieCatalogApplication

    private val userFavoritesViewModel : UserFavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_favorites, container, false)

        moviesList = arrayListOf()
        favoriteMoviesList = arrayListOf()
        etSearch = view.findViewById(R.id.etSearch)
        llSearchContainer = view.findViewById(R.id.llSearchContainer)
        ivSearch = view.findViewById(R.id.ivSearch)
        recyclerFavoriteMovies = view.findViewById(R.id.recyclerFavoriteMovies)

        recyclerFavoriteMovies.setHasFixedSize(true)
        recyclerFavoriteMovies.layoutManager = GridLayoutManager(view.context, 3)

        userFavoritesViewModel.getAllMovies.observe( viewLifecycleOwner) {
            moviesList.clear()
            moviesList.addAll(it)
            favoriteMoviesList = userFavoritesViewModel.getFavoriteMovies(moviesList)

            loadMovies(favoriteMoviesList)

            ivSearch.setOnClickListener { performSearch(etSearch.text.toString()) }

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    performSearch(s.toString())
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    performSearch(s.toString())
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    performSearch(s.toString())
                }
            })

        }

        return view
    }

    private fun loadMovies ( movieSearchList : ArrayList<MovieEntity> ) {
        val favoriteMoviesAdapter = MovieAdapter(movieSearchList, application)
        recyclerFavoriteMovies.adapter = favoriteMoviesAdapter
        favoriteMoviesAdapter.setOnClickListener(object : MovieAdapter.OnMovieItemClickListener {
            override fun onMovieImageClick(position: Int) {
                val currentItem = movieSearchList[position]
                userFavoritesViewModel.setMovieToNavigate(currentItem.title)
                Navigation.findNavController(requireView()).navigate(R.id.from_userFavoritesFragment_to_movieDetailsFragment)
            }
        })
    }

    private fun performSearch (keyword : String) {
        userFavoritesViewModel.setSearchKeyword(etSearch.text.toString())
        val filteredMovies = userFavoritesViewModel.getFilteredFavoriteMovies( favoriteMovies = favoriteMoviesList, keyWord = keyword )
        loadMovies(filteredMovies)
    }

}