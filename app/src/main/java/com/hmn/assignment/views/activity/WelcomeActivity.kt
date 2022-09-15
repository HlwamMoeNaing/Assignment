package com.hmn.assignment.views.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hmn.assignment.MainActivity
import com.hmn.assignment.R
import com.hmn.assignment.data.local.MovieDatabase
import com.hmn.assignment.data.net.MoviesApi
import com.hmn.assignment.repo.MoviesRepository
import com.hmn.assignment.viewmodel.MoviesViewModel
import com.hmn.assignment.viewmodel.ViewModelFactory

class WelcomeActivity : AppCompatActivity() {

    lateinit var viewModel: MoviesViewModel
    lateinit var repository: MoviesRepository
    private lateinit var factory: ViewModelFactory

lateinit var database: MovieDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        repository = MoviesRepository(this@WelcomeActivity, MoviesApi())
        factory = ViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)
        database = MovieDatabase.getDatabase(this)
        val dao = database.allMovieDao()

        val savedMovie = dao.getAllMovies()
        networkCall()


        if (!savedMovie.isEmpty()) {
            startActivity()
        }





    }
    override fun onRestart() {
        super.onRestart()
        if (isConnectingToInternet(this)) {
            startActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isConnectingToInternet(this)) {
            startActivity()
        }
    }

    private fun networkCall() {
        val dao =database.allMovieDao()
        val list = dao.getAllMovies()

        if (isConnectingToInternet(this)) {
            if (list.isEmpty()) {
                viewModel.getAllSavedList()
                viewModel.allEntity.observe(this, Observer {
                    repository.insertAllMovies(it)
                })


            }
        } else {
            Toast.makeText(this, "Connection Error..", Toast.LENGTH_LONG).show()
        }
    }

    fun startActivity() {
        Handler().postDelayed(
            {
                val i = Intent(this, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
                finish()

            },
            2000
        )
    }

    private fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info)
                    if (i.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
        }
        return false
    }


}

