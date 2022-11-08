package com.example.themoviecatalog.fragments.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
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
class HomeFragment : Fragment() {

    private lateinit var nestedScrollMovies : NestedScrollView
    private lateinit var recyclerMovies : RecyclerView
    private lateinit var pbHomeLoading : ProgressBar
    private lateinit var moviesList : ArrayList<MovieEntity>

    @Inject
    lateinit var application: MovieCatalogApplication

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        moviesList = arrayListOf()
        nestedScrollMovies = view.findViewById(R.id.nestedScrollMovies)
        pbHomeLoading = view.findViewById(R.id.pbHomeLoading)
        recyclerMovies = view.findViewById(R.id.recyclerMovies)

        recyclerMovies.setHasFixedSize(true)
        recyclerMovies.layoutManager = GridLayoutManager(view.context, 3)

        nestedScrollMovies.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{ v, _, scrollY, _, _ ->
            if ( scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight ){
                homeViewModel.getMoviesFromApi()
                pbHomeLoading.visibility = View.VISIBLE
                Handler().postDelayed({
                    pbHomeLoading.visibility = View.GONE
                }, 2000)
            }
        })

        homeViewModel.checkForLocalDataAvailability()
        homeViewModel.getAllMovies.observe( viewLifecycleOwner) {
            moviesList.clear()
            moviesList.addAll(it)
            if (moviesList.size >= 10) {
                val moviesAdapter = MovieAdapter(moviesList, application)
                recyclerMovies.adapter = moviesAdapter
                moviesAdapter.setOnClickListener(object : MovieAdapter.OnMovieItemClickListener {
                    override fun onMovieImageClick(position: Int) {
                        val currentItem = moviesList[position]
                        homeViewModel.setMovieToNavigate(currentItem.title)
                        Navigation.findNavController(requireView())
                            .navigate(R.id.from_homeFragment_to_movieDetailsFragment)
                    }
                })
            }

        }


        return view
    }


}