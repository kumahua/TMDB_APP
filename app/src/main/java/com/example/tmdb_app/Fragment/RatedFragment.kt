package com.example.tmdb_app.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_app.Constants.MOVIE_BACKDROP
import com.example.tmdb_app.Constants.MOVIE_OVERVIEW
import com.example.tmdb_app.Constants.MOVIE_POSTER
import com.example.tmdb_app.Constants.MOVIE_RATING
import com.example.tmdb_app.Constants.MOVIE_RELEASE_DATE
import com.example.tmdb_app.Constants.MOVIE_TITLE
import com.example.tmdb_app.MovieDetailsActivity
import com.example.tmdb_app.MoviesAdapter
import com.example.tmdb_app.R
import com.example.tmdb_app.Result
import com.example.tmdb_app.Util.MoviesRepository
import com.example.tmdb_app.databinding.FragmentRatedBinding

class RatedFragment : Fragment(R.layout.fragment_rated) {
    private lateinit var ratedMovies: RecyclerView
    private lateinit var ratedMoviesAdapter: MoviesAdapter
    private lateinit var binding: FragmentRatedBinding
    private lateinit var ratedMoviesLayoutMgr: LinearLayoutManager

    private var ratedMoviesPage: Int = 1
    private var spanArray: IntArray? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRatedBinding.inflate(inflater, container, false)

        ratedMoviesLayoutMgr =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        //LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        ratedMoviesPage = 1
        ratedMovies = binding.ratedMovies
        ratedMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie)}
        ratedMovies.layoutManager = ratedMoviesLayoutMgr
        ratedMovies.adapter = ratedMoviesAdapter

        getRatedMovies()

        return binding.root
    }

    private fun getRatedMovies() {
        MoviesRepository.getRatedMovies(
            ratedMoviesPage,
            ::onRatedMoviesFetched,
            ::onError
        )
    }

    private fun onRatedMoviesFetched(movies: List<Result>) {
        ratedMoviesAdapter.appendMovies(movies)
        attachRatedMoviesOnScrollListener()
    }

    private fun attachRatedMoviesOnScrollListener() {
        ratedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //totalItemCount???popularMoviesAdapter??????????????????
                val totalItemCount = ratedMoviesLayoutMgr.itemCount
                //?????????????????????????????????RecyclerView ???????????????????????????
                val visibleItemCount = ratedMoviesLayoutMgr.childCount
                //findFirstVisibleItemPosition ????????????????????????????????????
                val firstVisibleItem = ratedMoviesLayoutMgr.findFirstVisibleItemPosition()


                /*
                ??????????????????????????????????????????visibleItemCount???????????????
                ??????????????????????????????OnScrollListener????????????????????????????????????????????????
                ????????????????????????popularMoviesPage???????????????getPopularMovies()???
                */
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    ratedMovies.removeOnScrollListener(this)
                    ratedMoviesPage++
                    getRatedMovies()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(result: Result) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, result.backdropPath)
        intent.putExtra(MOVIE_POSTER, result.posterPath)
        intent.putExtra(MOVIE_TITLE, result.title)
        intent.putExtra(MOVIE_RATING, result.voteAverage)
        intent.putExtra(MOVIE_RELEASE_DATE, result.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, result.overview)
        startActivity(intent)
    }
}