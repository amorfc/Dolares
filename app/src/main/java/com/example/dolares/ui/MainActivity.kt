package com.example.dolares.ui

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


    val navController = findNavController(R.id.nav_graph)

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
              R.id.coresFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.capsulesFragment -> {
                   bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.settingsFragment -> {
                   bottomNavigationView.visibility = View.VISIBLE
                }
                    R.id.launchpadsFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                          }
           }
        }
    }
}