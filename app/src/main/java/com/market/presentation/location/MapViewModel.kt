package com.market.presentation.location

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.market.data.models.get.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val sharedPreferences: SharedPreferences):ViewModel() {

    fun storeLocation(latitude:String, longitude:String){

        sharedPreferences.edit().putString("latitude",latitude).commit()
        sharedPreferences.edit().putString("longitude",longitude).commit()

    }
}