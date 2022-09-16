package com.hmn.assignment.data.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    //https://api.themoviedb.org/3/movie/latest?api_key=c11dcb567483000f07d05199bf19ef01

    //https://api.themoviedb.org/3/search/movie?api_key=c11dcb567483000f07d05199bf19ef01&language=en-US&page=1&include_adult=false
    @GET("movie/popular?api_key=c11dcb567483000f07d05199bf19ef01")
    suspend fun getPopularMovies(): Response<Movies>

    @GET("movie/top_rated?api_key=c11dcb567483000f07d05199bf19ef01")
    suspend fun getTopMovies(): Response<Movies>

    @GET("movie/popular?api_key=c11dcb567483000f07d05199bf19ef01")
    suspend fun getLatestMovies(): Response<Movies>


    @GET("movie/{movieId}?api_key=c11dcb567483000f07d05199bf19ef01")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int, @Query("language") language: String): Response<MovieDetails>

//    @GET("search/movie")
//    fun searchMovie(@Query("api_key") api_key: String, @Query("query") q: String): Call<Movies>
@GET("search/movie?api_key=c11dcb567483000f07d05199bf19ef01")
suspend fun searchMovie( @Query("query") q: String): Movies
    companion object {
        operator fun invoke(): MoviesApi {
            val gson = GsonBuilder().setLenient().create()
            val okCli = OkHttpClient.Builder()
            val inteceptor = HttpLoggingInterceptor()
            inteceptor.level = HttpLoggingInterceptor.Level.BODY
            okCli.interceptors().add(inteceptor)
            val okclient = okCli.build()
            return Retrofit.Builder().client(okclient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.themoviedb.org/3/")
                .build()
                .create(MoviesApi::class.java)
        }
    }


}