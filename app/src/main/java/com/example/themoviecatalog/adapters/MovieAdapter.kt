package com.example.themoviecatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviecatalog.BuildConfig
import com.example.themoviecatalog.MovieCatalogApplication
import com.example.themoviecatalog.R
import com.example.themoviecatalog.database.room.entities.MovieEntity

class MovieAdapter (private val movieList : ArrayList<MovieEntity>, private val movieCatalogApplication: MovieCatalogApplication) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    //Create onClickListener
    private lateinit var mMovieItemClickListener : OnMovieItemClickListener
    interface OnMovieItemClickListener {
        fun onMovieImageClick(position: Int)
    }
    fun setOnClickListener(listenerMovie : OnMovieItemClickListener){
        mMovieItemClickListener = listenerMovie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.card_movie, parent, false)
        return MovieViewHolder(itemView, mMovieItemClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieList[position]
        holder.tvMovieName.text = currentItem.title
        val movieSnapshot = BuildConfig.TMDB_API_IMAGES_BASE_URL + "original/${currentItem.posterPath}"
        Glide.with(movieCatalogApplication).load(movieSnapshot).centerCrop().into(holder.ivMovieSnapshot)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieViewHolder (itemView : View, listenerMovie : OnMovieItemClickListener ) : RecyclerView.ViewHolder(itemView){

        val tvMovieName : TextView = itemView.findViewById(R.id.tvMovieName)
        val ivMovieSnapshot : ImageView = itemView.findViewById(R.id.ivMovieSnapshot)

        init {
            ivMovieSnapshot.setOnClickListener{
                listenerMovie.onMovieImageClick(adapterPosition)
            }
        }

    }

}