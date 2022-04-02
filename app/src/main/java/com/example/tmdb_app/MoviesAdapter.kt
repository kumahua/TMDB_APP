package com.example.tmdb_app

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.tmdb_app.databinding.ItemMovieBinding

class MoviesAdapter (
    private var movies: MutableList<Result>,
    private val onMovieClick: (result: Result) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        private val poster: ImageView = binding.itemMoviePoster
        private val movieName: TextView = binding.itemMovieName

        fun bind(movie: Result) {
            //Glide 主要用來載入圖片，支持 Jpg png gif webp，也支持從網路載入
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            movieName.text = movie.title

            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun appendMovies(movies: List<Result>){
        this.movies.addAll(movies)
        //startPosition: 起始位置，插入資料的位置。
        //itemCount:資料個數。
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }
}