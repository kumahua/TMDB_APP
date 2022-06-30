# TMDB_APP

<p align="center">
	<img src="https://user-images.githubusercontent.com/40682280/176609526-5c30309e-f35b-43db-96e6-ea8ae47c5997.png" width="350"> <img src="https://user-images.githubusercontent.com/40682280/176609587-9f53f3fa-46ad-4a32-8eca-ffc674d39156.png" width="350">
<p>

## MoviesRepository
	object MoviesRepository {

	     private val api: Api

	     init {
		 val retrofit = Retrofit.Builder()
		     .baseUrl("https://api.themoviedb.org/3/")
		     .addConverterFactory(GsonConverterFactory.create())
		     .build()

		 api = retrofit.create(Api::class.java)
	     }
	 }
使用初始化實例時調用的 Kotlin 的 init block，我們使用它的builder實例化一個 Retrofit 實例。接著，使用 Retrofit實例實例化一個 Api 實例。
	
## Callbacks 使用Kotlin的高階函數
在 Kotlin 中，我們可以將一個函數傳遞給另一個函數，並在 Kotlin 中調用它。—— 高階函數
	
	fun getPopularMovies(
	 page: Int = 1,
	 onSuccess: (movies: List<Movie>) -> Unit,
	 onError: () -> Unit
	     ) {
		 ...
	     }
	
onSuccess 是一個參數，它是一個不返回任何內容 -> Unit 但它接受List<Movie>的函數。
onError 與 onSuccess 相同，但它不接受任何內容。 我們需要做的只是調用這個方法。
	
	 fun getPopularMovies(
		 page: Int = 1,
		 onSuccess: (movies: List<Movie>) -> Unit,
		 onError: () -> Unit
	     ) {
		 api.getPopularMovies(page = page)
		     .enqueue(object : Callback<GetMoviesResponse> {
			 override fun onResponse(
			     call: Call<GetMoviesResponse>,
			     response: Response<GetMoviesResponse>
			 ) {
			     if (response.isSuccessful) {
				 val responseBody = response.body()

				 if (responseBody != null) {
				     onSuccess.invoke(responseBody.movies)
				 } else {
				     onError.invoke()
				 }
			     } else {
				 onError.invoke()
			     }
			 }

			 override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
			     onError.invoke()
			 }
		     })
	     }
	
invoke() 是執行高階函數的方式。它會根據高階函數是否具有參數而有所不同。
onSuccess: (movies: List<Movie>) -> 單位是 onSuccess.invoke(responseBody.movies)。
onError: () -> 單位是 onError.invoke()
