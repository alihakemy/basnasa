package com.market.presentation.mainscreen.user


import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.market.R
import com.market.data.models.get.popups.popups
import com.market.databinding.ActivityMainUserBinding
import com.market.popups.DialogPopUp
import com.market.presentation.bases.BaseActivity
import com.market.presentation.mainscreen.user.ui.home.HomeViewModel
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivityUser : BaseActivity() {

    private lateinit var binding: ActivityMainUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main_user)

        navView.setupWithNavController(navController)
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        homeViewModel.getPopups()
        homeViewModel.popupss.observe(this, Observer {

            when (val result = it) {
                is ResultState.Success<popups> -> {
                    DialogPopUp(this,result.data).show()
                }
                else ->{

                }
            }

        })


    }


}