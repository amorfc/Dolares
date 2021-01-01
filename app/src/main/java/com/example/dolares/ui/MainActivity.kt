package com.example.dolares.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dolares.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    val navController = findNavController(R.id.nav_host_fragment)

     val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        findViewById<BottomNavigationView>(R.id.bottom_navigation)
         .setupWithNavController(navController)

       navController.addOnDestinationChangedListener { _, destination, _ ->
         when (destination.id) {
          //  R.id.loginFragment -> {
              //      bottomNavigationView.visibility = View.GONE
               // }
            // R.id.registerFragment -> {
            //        bottomNavigationView.visibility = View.GONE
               // }
              R.id.CoresFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.CapsuleFragment -> {
                   bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.settingsFragment -> {
                   bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.launchPadsFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                          }
           }
        }
    }
}