package com.hmn.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.data.net.MovieDetails
import com.hmn.assignment.data.net.MoviesApi
import com.hmn.assignment.data.net.Result
import com.hmn.assignment.repo.MoviesRepository
import kotlinx.coroutines.*

class MoviesViewModel(
    private val repository: MoviesRepository,

) : ViewModel() {


    var job: Job? = null
    val MovieDetails = MutableLiveData<MovieDetails>()
    var searchResult = MutableLiveData<List<Result>>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private val _allEntity = MutableLiveData<List<AllMovieEntity>>()
    val allEntity: LiveData<List<AllMovieEntity>>
        get() = _allEntity

    fun getMovieDetails(movieId : Int,language: String) {
        fetchMovieDetails(movieId,language)
    }


    private fun fetchMovieDetails(movieId: Int,language: String) {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val apiService = MoviesApi()

            val response = apiService.getMovieDetails(movieId,language)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    MovieDetails.value = response.body()

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }




    fun getSearch(str: String)=viewModelScope.launch {

        job = CoroutineScope(Dispatchers.IO +exceptionHandler).launch {
            val apiService = MoviesApi()
            val resp = apiService.searchMovie(str)
            withContext(Dispatchers.Main){
                searchResult.value = resp.results
            }

            /*
            * In the api service if you want to use Obserable, delete suspend. or not delete obserable
            * */


//            resp.forEach {
//                myRes = it.results
//            }
//            withContext(Dispatchers.Main){
//             searchResult.postValue(myRes)
//            }


        }





    }

     fun getAllSavedList() = viewModelScope.launch {

        val popularDeferred = async { repository.getPopularMovies() }
        val topRateDeferred = async { repository.getTopMovies() }
        val latestDeferred = async { repository.getLatestMovies() }


        val popular = popularDeferred.await()
        val topMov = topRateDeferred.await()
        val latest = latestDeferred.await()


        val catList = ArrayList<AllMovieEntity>()
        val res = popular.results
        val top = topMov.results
        val lat = latest.results
        for (data in res) {
            val pop = AllMovieEntity(
                0,
                data.id,
                data.title,
                data.original_language,
                data.release_date,
                data.backdrop_path,
                data.overview,
                data.vote_count,
                data.poster_path,
                "Popular Movies",
                false
            )
            catList.add(pop)
            _allEntity.postValue(catList)


        }
        for (data in top) {
            val to = AllMovieEntity(
                0,
                data.id,
                data.title,
                data.original_language,
                data.release_date,
                data.backdrop_path,
                data.overview,
                data.vote_count,
                data.poster_path,
                "Top Movies",
                false


            )
            catList.add(to)
            _allEntity.postValue(catList)


        }

        for (data in lat) {
            val la = AllMovieEntity(
                0,
                data.id,
                data.title,
                data.original_language,
                data.release_date,
                data.backdrop_path,
                data.overview,
                data.vote_count,
                data.poster_path,
                "Latest Movies",
                false
            )
            catList.add(la)
           _allEntity.postValue(catList)

      }

    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun onError(message: String) {

        GlobalScope.launch {
            withContext(Dispatchers.Main){
                Log.d("wkk","Something wrong ")

            }
        }
    }
}
