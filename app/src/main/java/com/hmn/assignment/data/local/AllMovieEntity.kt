package com.hmn.assignment.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "all_movies")
class AllMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var _id: Int,


    @ColumnInfo(name = "id")
    val mid: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "original_language")
    val original_language: String,

    @ColumnInfo(name = "release_date")
    val release_date: String,

    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "vote_count")
    val vote_count: Int,

    @ColumnInfo(name = "poster_path")
    val poster_path: String,

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "isFav")
    var isFav: Boolean = false


)

