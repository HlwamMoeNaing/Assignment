package com.hmn.assignment.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hmn.assignment.R
import com.hmn.assignment.data.net.MoviesApi
import com.hmn.assignment.repo.MoviesRepository
import com.hmn.assignment.viewmodel.MoviesViewModel
import com.hmn.assignment.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    lateinit var movie_tagline:TextView
    lateinit var tvd_title:TextView
    lateinit var movie_release_date:TextView
    lateinit var movie_rating:TextView

    lateinit var movie_runtime:TextView
    lateinit var movie_budget:TextView
    lateinit var movie_revenue:TextView
    lateinit var movie_overview:TextView
    lateinit var iv_movie_poster:ImageView
    lateinit var viewModel: MoviesViewModel
    lateinit var repository: MoviesRepository
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        movie_tagline = findViewById(R.id.movie_tagline)




        tvd_title = findViewById(R.id.tvd_title)
        movie_release_date =findViewById(R.id.movie_release_date)
        movie_rating =findViewById(R.id.movie_rating)
        movie_runtime =findViewById(R.id.movie_runtime)
        movie_budget= findViewById(R.id.movie_budget)
        movie_revenue=findViewById(R.id.movie_revenue)
        movie_overview = findViewById(R.id.movie_overview)
        iv_movie_poster = findViewById(R.id.iv_movie_poster)


        repository = MoviesRepository(this@DetailActivity, MoviesApi())
        factory = ViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)

        val movieId = intent.getIntExtra("id",0)
        Log.d("bundle",movieId.toString())
        print("this is ${movieId}")

        viewModel.getMovieDetails(movieId,"en-US")


        viewModel.MovieDetails.observe(this, Observer {data->
            movie_tagline.text = data.tagline
            print("this is ${data.title}")
            tvd_title.text = data.title
            movie_release_date.text = data.releaseDate
            movie_rating.text = "Rating- "+data.rating.toString()
            movie_runtime.text = data.runtime.toString() +" -Minutes"
            movie_budget.text = "Budget -"+data.budget.toString()
            movie_revenue.text ="Revenue -"+ data.revenue.toString()
            movie_overview.text = data.overview
            val posterUrl = "https://image.tmdb.org/t/p/w500/" + data.posterPath

            Picasso.get().load(posterUrl).into(iv_movie_poster)
        })


    }
}