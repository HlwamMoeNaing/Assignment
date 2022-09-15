package com.hmn.assignment.data.local

import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(entity: List<AllMovieEntity>)

    @Update
    fun updateMovies(entity: AllMovieEntity)

    @Delete
    suspend  fun deleteMovies(entity: AllMovieEntity)

    @Query("Delete From all_movies")
    suspend fun deleteMovies()

    @Query("SELECT * From all_movies")
    fun getAllMovies(): List<AllMovieEntity>

    @Query("SELECT * From all_movies Where category LIKE :mCategory")
    fun getDataFromCategory(mCategory: String): List<AllMovieEntity>


    @Query("SELECT COUNT(*) FROM all_movies")
    fun getCount(): Int

    @Query("SELECT * FROM all_movies WHERE isFav =:isFav")
    fun getFv(isFav: Boolean):List<AllMovieEntity>

}