package com.hmn.assignment.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hmn.assignment.R
import com.hmn.assignment.adapter.SearchListAdapter
import com.hmn.assignment.data.net.MoviesApi
import com.hmn.assignment.repo.MoviesRepository
import com.hmn.assignment.utils.action
import com.hmn.assignment.utils.snack
import com.hmn.assignment.viewmodel.MoviesViewModel
import com.hmn.assignment.viewmodel.ViewModelFactory


class SearchFragment : Fragment() {

    lateinit var searchProgressBar: ProgressBar
    lateinit var searchRecyclerView: RecyclerView
    lateinit var searchLayout: ConstraintLayout
    private lateinit var factory: ViewModelFactory
    private var adapter = SearchListAdapter(mutableListOf()) { }
    private lateinit var viewModel: MoviesViewModel
    lateinit var repository: MoviesRepository
    lateinit var editText: EditText
    lateinit var searchButton: ImageButton
    lateinit var keyword: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        editText = view.findViewById(R.id.ed_search)
        searchButton = view.findViewById(R.id.enter_to_search)
        searchLayout = view.findViewById(R.id.searchLayout)
        searchProgressBar = view.findViewById(R.id.searchProgressBar)
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView)

        keyword = editText.text.toString()
        repository = MoviesRepository(requireContext(), MoviesApi())
        factory = ViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)

        searchRecyclerView.adapter = adapter

        searchButton.setOnClickListener {
            if (editText.text.isNotBlank()) {
                searchMovie("avenger")
                hideLoading()
            }
        }

    }


    private fun showLoading() {
        searchProgressBar.visibility = View.VISIBLE
        searchRecyclerView.isEnabled = false
    }

    private fun hideLoading() {
        searchProgressBar.visibility = View.GONE
        searchRecyclerView.isEnabled = true
    }

    private fun showMessage() {
        searchLayout.snack(getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.ok)) {
                searchMovie(keyword)
            }


        }
    }


    private fun searchMovie(str: String) {
        showLoading()

        viewModel.searchMovie(str).observe(viewLifecycleOwner, Observer { movies ->
            hideLoading()
            if (movies == null) {
                showMessage()
            } else {
                adapter.setMovies(movies)
            }
        })
    }
}