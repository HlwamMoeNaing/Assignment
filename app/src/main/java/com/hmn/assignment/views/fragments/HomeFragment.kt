package com.hmn.assignment.views.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hmn.assignment.R
import com.hmn.assignment.adapter.MultiRecycler
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.data.net.Category
import com.hmn.assignment.data.net.MoviesApi
import com.hmn.assignment.repo.MoviesRepository
import com.hmn.assignment.utils.Constant
import com.hmn.assignment.utils.RecyclerViewClickInterface
import com.hmn.assignment.utils.TestListener
import com.hmn.assignment.viewmodel.MoviesViewModel
import com.hmn.assignment.viewmodel.ViewModelFactory
import java.util.ArrayList


class HomeFragment : Fragment() {

    private lateinit var mViewModel: MoviesViewModel
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: MultiRecycler
    lateinit var repository: MoviesRepository
    private lateinit var factory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = MoviesRepository(requireContext(), MoviesApi())


        factory = ViewModelFactory(repository)
        mViewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.fragment_main_recycler)
        start()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun start() {
        mViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]


        val entity = repository.getAllVideos()
        val popular = entity.toMutableList()
        val top = entity.toMutableList()
        val latest = entity.toMutableList()
        top.removeIf { s -> !s.category.contains("Top Movies") }
        popular.removeIf { s -> !s.category.contains("Popular") }
        latest.removeIf { s -> !s.category.contains("Latest Movies") }


        val catList = ArrayList<Category>()
        catList.add(Category(Constant.BAN, "Popular", popular))
        catList.add(Category(Constant.TOP, "Top Videos", top))
        catList.add(Category(Constant.LAT, "Latest Video", latest))

        Log.d("@test2", catList.toString())




        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)
        adapter = MultiRecycler(requireContext(), catList)
        recycler.adapter = adapter

    }




}