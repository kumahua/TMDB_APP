package com.example.tmdb_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.tmdb_app.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backdrop = binding.movieBackdrop
        poster = binding.moviePoster
        movieTitle = binding.movieTitle
        rating = binding.movieRating
        releaseDate = binding.movieReleaseDate
        overview = binding.movieOverview

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        setSupportActionBar(binding.toolbarMovieDetails)
        if(supportActionBar != null) {
            supportActionBar?.apply{
                setDisplayHomeAsUpEnabled(true)
                title = "Movie Details"
            }
        }
        binding.toolbarMovieDetails.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(Constants.MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(Constants.MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        movieTitle.text = extras.getString(Constants.MOVIE_TITLE, "")
        rating.rating = extras.getFloat(Constants.MOVIE_RATING, 0F)
        releaseDate.text = extras.getString(Constants.MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(Constants.MOVIE_OVERVIEW, "")
    }
}