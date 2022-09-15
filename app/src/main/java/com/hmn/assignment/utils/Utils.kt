package com.hmn.assignment.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hmn.assignment.data.local.AllMovieEntity
import com.hmn.assignment.data.net.Result

interface RecyclerViewListener {
    fun onRecyclerViewItemClick(view: View, result: Result)
}

interface TestListener {
    fun onItemClick(list: AllMovieEntity)
}

interface RecyclerViewClickInterface {
    fun onDetailClick(position:Int, allMovieEntity: AllMovieEntity)
    fun onItemCategoryClick(position:Int, allMovieEntity: AllMovieEntity)
}

object Constant {

    const val BAN = 1
    const val TOP = 2
    const val LAT = 4

}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_INDEFINITE, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

