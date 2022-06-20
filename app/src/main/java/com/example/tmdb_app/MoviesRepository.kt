package com.example.tmdb_app

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    private val api: Api

    //object中的init區塊只會在初始化時執行並只執行一次
    init {
        //建立 Retrofit 實例
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        //onSuccess是一個參數，也是一個高級函數，接受List<Result>，但不返回任何值
        onSuccess: (movies: List<Result>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(page = page)
            .enqueue(object: Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful){
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.results)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    onError.invoke()
                }

            })
    }

    fun getRatedMovies(
        page: Int = 1,
        onSuccess: (movies: List<Result>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopRatedMovies(page = page)
            .enqueue(object: Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful){
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.results)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    onError.invoke()
                }

            })
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: List<Result>) -> Unit,
        onError: () -> Unit
    ) {
        api.getUpcomingMovies(page = page)
            .enqueue(object: Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful){
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.results)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    onError.invoke()
                }

            })
    }
}