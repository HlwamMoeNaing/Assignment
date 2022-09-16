package com.hmn.assignment.repo

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.data.local.MovieDao
import com.hmn.assignment.data.local.MovieDatabase
import com.hmn.assignment.data.net.Movies
import com.hmn.assignment.data.net.MoviesApi
import com.hmn.assignment.data.net.Result
import com.hmn.assignment.data.net.SafeApiRequest
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