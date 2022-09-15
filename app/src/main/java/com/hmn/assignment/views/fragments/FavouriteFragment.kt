package com.hmn.assignment.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hmn.assignment.R
import com.hmn.assignment.adapter.FavouriteAdp
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.data.local.MovieDatabase
import com.hmn.assignment.utils.TestListener


class FavouriteFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.favourite_recycler)
        val list = MovieDatabase.getDatabase(requireContext()).allMovieDao().getFv(true)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.adapter = FavouriteAdp(requireContext(),list.toMutableList())
    }




}