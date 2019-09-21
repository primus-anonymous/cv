package com.neocaptainnemo.cv

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onSupportNavigateUp() =
            findNavController(R.id.navHostFragment).navigateUp()
}
