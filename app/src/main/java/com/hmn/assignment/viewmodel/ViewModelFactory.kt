package com.hmn.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.hmn.assignment.repo.MoviesRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: MoviesRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MoviesViewModel(repository) as T
    }

}