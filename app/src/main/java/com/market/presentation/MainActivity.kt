package com.market.presentation

import android.content.Intent
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.market.databinding.ActivityMainBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.onboarding.OnBoarding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity() : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkIsLogin()) {


            if (getLoginData().Roles.equals("Tager")) {


            } else {
                if (getLocation()) {
                    val intent = Intent(this, MainActivityUser::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }


        } else {

            val intent = Intent(this, OnBoarding::class.java)
            startActivity(intent)
            finish()
        }

    }


}