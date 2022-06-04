package com.market

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import java.text.NumberFormat
import java.util.*

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, "AIzaSyC2SNZwXMICS8FEfqU72VN9EkfOEEuhKyc")
        NumberFormat.getInstance(Locale.ENGLISH)

    }
}