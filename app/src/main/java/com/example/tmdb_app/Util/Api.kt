package com.example.tmdb_app.Util

import com.example.tmdb_app.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") api_key: String="affcd470da3f0ef01b930fad1a75d1ed",
        @Query("language") language: String = "zh-TW",
        @Query("page") page: Int
    ): retrofit2.Call<Movie>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "affcd470da3f0ef01b930fad1a75d1ed",
        @Query("language") language: String = "zh-TW",
        @Query("page") page: Int
    ): retrofit2.Call<Movie>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "affcd470da3f0ef01b930fad1a75d1ed",
        @Query("language") language: String = "zh-TW",
        @Query("page") page: Int
    ): retrofit2.Call<Movie>
}