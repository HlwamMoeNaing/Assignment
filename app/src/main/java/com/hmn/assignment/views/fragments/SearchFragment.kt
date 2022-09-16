package com.hmn.assignment.views.fragments

import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
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


    private lateinit var viewModel: MoviesViewModel

    lateinit var editText: EditText
    lateinit var searchButton: ImageButton
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


        val apiserv = MoviesApi()
        val repo = MoviesRepository(requireContext(),apiserv)
        val fact = ViewModelFactory(repo)
        viewModel = ViewModelProviders.of(this, fact).get(MoviesViewModel::class.java)

        searchButton.setOnClickListener {

            Log.d("@search","Searching")


            if(editText.text.isNotBlank()){
                showLoading()
                viewModel.getSearch(editText.text.toString())
                viewModel.searchResult.observe(viewLifecycleOwner, Observer {
                    val adapter = SearchListAdapter(it.toMutableList(),{})
                    searchRecyclerView.adapter = adapter
                    searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

                    hideLoading()
                })
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



}