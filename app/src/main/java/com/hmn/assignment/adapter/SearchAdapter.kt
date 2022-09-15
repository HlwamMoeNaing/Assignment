package com.hmn.assignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.hmn.assignment.R
import com.hmn.assignment.data.net.Result
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class SearchListAdapter(private val movies: MutableList<Result>,
                        private var listener: (Result) -> Unit) : RecyclerView.Adapter<SearchListAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fav_item, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int = movies.size ?: 0

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position], position)
    }

    fun setMovies(movieList: List<Result>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()
    }



    inner class MovieHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val categoryImage: AppCompatImageView = view.findViewById(R.id.favourite_image)
        val title :TextView = view.findViewById(R.id.favourite_title)

        fun bind(movie: Result, position: Int) = with(view) {
    title.text = movie.title
            //searchReleaseDateTextView.text = movie.release_date
            view.setOnClickListener { listener(movies[position]) }
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.poster_path).into(categoryImage)
        }
    }
}