package com.hmn.assignment.repo

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.data.local.MovieDao
import com.hmn.assignment.data.local.MovieDatabase
import com.hmn.assignment.data.net.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository(
    context: Context,
    private val api: MoviesApi
) : SafeApiRequest() {


    private val database = MovieDatabase.getDatabase(context)

    private val movieDao = database.allMovieDao()
    private val getAllVideos = movieDao.getAllMovies()

    suspend fun getPopularMovies() = apiRequest { api.getPopularMovies() }
    suspend fun getTopMovies() = apiRequest { api.getTopMovies() }
    suspend fun getLatestMovies() = apiRequest { api.getLatestMovies() }



   fun searchMovies(query: String): LiveData<List<Result>?> {

        val data = MutableLiveData<List<Result>>()
api.searchMovie("c11dcb567483000f07d05199bf19ef01",query).
        enqueue(object : Callback<Movies> {
            override fun onFailure(call: Call<Movies>, t: Throwable) {

                Log.d(this.javaClass.simpleName, "Failure")
            }

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                data.value = response.body()?.results
                Log.d("hmn", "Response: ${response.body()?.results}")
            }
        })
        return data
    }

    fun insertAllMovies(movies: List<AllMovieEntity>) {
        InsertAllBanner(movieDao).execute(movies)
    }

    fun getAllVideos(): List<AllMovieEntity> {
        return getAllVideos
    }



}

class InsertAllBanner(dao: MovieDao) : AsyncTask<List<AllMovieEntity>, Void, Void>() {
    private val videoDao: MovieDao = dao

    override fun doInBackground(vararg p0: List<AllMovieEntity>?): Void? {
        videoDao.insertAllMovies(p0[0]!!)
        return null
    }
}