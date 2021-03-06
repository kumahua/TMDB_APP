package com.example.tmdb_app.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_app.*
import com.example.tmdb_app.Util.MoviesRepository
import com.example.tmdb_app.databinding.FragmentPopularBinding

class PopularFragment : Fragment(R.layout.fragment_popular) {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var binding: FragmentPopularBinding
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private var popularMoviesPage: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPopularBinding.inflate(inflater, container, false)

        popularMoviesLayoutMgr =
            LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        //LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        popularMoviesPage = 1
        popularMovies = binding.popularMovies
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie)}
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMovies.adapter = popularMoviesAdapter

        getPopularMovies()

        return binding.root
    }

    private fun getPopularMovies() {
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Result>) {
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //totalItemCount???popularMoviesAdapter??????????????????
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                //?????????????????????????????????RecyclerView ???????????????????????????
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                //findFirstVisibleItemPosition ????????????????????????????????????
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()


                /*
                ??????????????????????????????????????????visibleItemCount???????????????
                ??????????????????????????????OnScrollListener????????????????????????????????????????????????
                ????????????????????????popularMoviesPage???????????????getPopularMovies()???
                */
                if (firstVisibleItem + visibleItemCount >= totalItemCount/2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(result: Result) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(Constants.MOVIE_BACKDROP, result.backdropPath)
        intent.putExtra(Constants.MOVIE_POSTER, result.posterPath)
        intent.putExtra(Constants.MOVIE_TITLE, result.title)
        intent.putExtra(Constants.MOVIE_RATING, result.voteAverage)
        intent.putExtra(Constants.MOVIE_RELEASE_DATE, result.releaseDate)
        intent.putExtra(Constants.MOVIE_OVERVIEW, result.overview)
        startActivity(intent)
    }
}