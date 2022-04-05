package com.example.tmdb_app

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_app.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var binding: FragmentUpcomingBinding
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private var upcomingMoviesPage: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        upcomingMoviesLayoutMgr =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        //LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        upcomingMoviesPage = 1
        upcomingMovies = binding.upcomingMovies
        upcomingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie)}
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMovies.adapter = upcomingMoviesAdapter

        getUpcomingMovies()

        return binding.root
    }

    private fun getUpcomingMovies() {
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            ::onUpcomingMoviesFetched,
            ::onError
        )
    }

    private fun onUpcomingMoviesFetched(movies: List<Result>) {
        upcomingMoviesAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //totalItemCount為popularMoviesAdapter中的電影總數
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                //當前被反覆回收的附加到RecyclerView 的子視圖的當前數量
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                //findFirstVisibleItemPosition 顯示畫面的第一個項目位置
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()


                /*
                如果用戶滾動到一半，加上一個visibleItemCount的緩沖值。
                滿足條件後，我們禁用OnScrollListener，因為我們只希望此代碼運行一次。
                接下來，我們增加popularMoviesPage，然後調用getPopularMovies()。
                */
                if (firstVisibleItem + visibleItemCount >= totalItemCount/2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
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