package com.hmn.assignment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.hmn.assignment.R
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.data.local.MovieDatabase
import com.hmn.assignment.utils.RecyclerViewClickInterface
import com.hmn.assignment.utils.TestListener
import com.hmn.assignment.views.activity.DetailActivity
import com.squareup.picasso.Picasso

class CategoryAdapter(
    val context: Context,
    val list: MutableList<AllMovieEntity>,



) : RecyclerView.Adapter<CategoryAdapter.MViewHolder>(){
    inner class MViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val categoryImage: AppCompatImageView = view.findViewById(R.id.image)
        val favImage = view.findViewById<AppCompatImageView>(R.id.img_add_fav)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.MViewHolder {
        val view =MViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item,parent,false)
        )
        return view
    }

    override fun onBindViewHolder(holder: CategoryAdapter.MViewHolder, position: Int) {

        val url =  "https://image.tmdb.org/t/p/w500" + list[position].poster_path
        Picasso.get().load(url).into(holder.categoryImage)

        val entity= list[position]

        if (entity.isFav) {
            holder.favImage.setImageResource(R.drawable.favourite_full)
        } else {
            holder.favImage.setImageResource(R.drawable.favourite_boarder)
        }

        holder.favImage.setOnClickListener {
            entity.isFav = !entity.isFav
            list[position] = entity

            MovieDatabase.getDatabase(context).allMovieDao().updateMovies(entity)
            notifyItemChanged(position, entity)
        }

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