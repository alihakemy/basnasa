package com.market.presentation.mainscreen.trader

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.market.R
import com.market.databinding.ActivityTaderMainBinding

class TaderMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityTaderMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }


}