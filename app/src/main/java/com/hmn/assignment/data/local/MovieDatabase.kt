package com.hmn.assignment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AllMovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun allMovieDao() :MovieDao

    companion object{
        private var INSTANCE:MovieDatabase?=null
        fun getDatabase(context: Context):MovieDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,MovieDatabase::class.java,"all movies")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE!!
        }
    }
}
