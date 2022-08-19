package com.market.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.view.WindowCompat
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.market.databinding.ActivityMainBinding
import com.market.presentation.bases.BaseActivity
import com.market.presentation.location.MapsActivity
import com.market.presentation.mainscreen.trader.TaderMainActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.presentation.mainscreen.user.displaytrader.TraderProfileActivity
import com.market.presentation.onboarding.OnBoarding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity() : BaseActivity() {
    private val SPLASH_DISPLAY_LENGTH = 1000

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* New Handler to start the Menu-Activity
       * and close this Splash-Screen after some seconds.*/
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */


        if (checkOnboard()) {
            if (getIntent().getData() != null) {


                FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(intent)

                    .addOnSuccessListener(this) { pendingDynamicLinkData ->
                        // Get deep link from result (may be null if no link is found)
                        var deepLink: Uri? = null
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.link
                            Log.e("linkalisami", deepLink.toString())

                            when (deepLink?.getQueryParameter("screenname")) {

                                "tager" -> {
                                    TraderProfileActivity.startTagerProfile( deepLink.getQueryParameter("tagerId").toString(), this)

                                    finishAffinity()
                                }else ->{
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
                            }
                        }

                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.

                    }
                    .addOnCanceledListener {

                    }

                    .addOnFailureListener {

                    }



            }
            else
            {
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



//            if (getLoginData().data.user.Roles.toString().toLowerCase().equals("tager")) {
//                if (getLocation()) {
//                    val intent = Intent(this, MainActivityUser::class.java)
//                    startActivity(intent)
//                    finish()
//
//
//
//                } else {
//                    val intent = Intent(this, MapsActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//
//            } else {
//                if (getLocation()) {
//                    val intent = Intent(this, MainActivityUser::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    val intent = Intent(this, MapsActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//
//            }


        } else {
            val intent = Intent(this, OnBoarding::class.java)
            startActivity(intent)
            finish()
        }
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }


}