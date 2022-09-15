package com.hmn.assignment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.hmn.assignment.R
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.data.local.MovieDatabase
import com.hmn.assignment.utils.TestListener
import com.hmn.assignment.views.activity.DetailActivity
import com.squareup.picasso.Picasso

class FavouriteAdp(val context: Context, val list: MutableList<AllMovieEntity>) :
    RecyclerView.Adapter<FavouriteAdp.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryImage: AppCompatImageView = view.findViewById(R.id.favourite_image)
        val title :TextView = view.findViewById(R.id.favourite_title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        val url =  "https://image.tmdb.org/t/p/w500" + list[position].poster_path
        Picasso.get().load(url).into(holder.categoryImage)

        holder.itemView.setOnClickListener {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra("id", list[position].mid)
            context.startActivity(i)

        }



    }

    override fun getItemCount(): Int {
        return list.size
    }

}