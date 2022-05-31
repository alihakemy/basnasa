package com.market.presentation.bases

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :ViewModel() {

    fun getSharedPreferences()=sharedPreferences
}