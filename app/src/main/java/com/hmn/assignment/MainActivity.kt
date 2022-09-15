package com.hmn.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hmn.assignment.data.net.MoviesApi
import com.hmn.assignment.repo.MoviesRepository
import com.hmn.assignment.viewmodel.MoviesViewModel
import com.hmn.assignment.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)

        val appBarConfig = AppBarConfiguration(setOf(R.id.homeFragment, R.id.favouriteFragment))
        setupActionBarWithNavController(navController, appBarConfig)
        bottomNavView.setupWithNavController(navController)
       //bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)





    }





}