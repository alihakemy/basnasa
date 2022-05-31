package com.market.presentation.bases

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.market.data.models.get.User


abstract class BaseActivity : AppCompatActivity() {

    private val viewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    fun checkIsLogin(): Boolean {
        return !viewModel.getSharedPreferences().getString("loginData", "").toString()
            .isNullOrEmpty()
    }

    fun getLocation(): Boolean {
        return !viewModel.getSharedPreferences().getString("latitude", "").toString()
            .isNullOrEmpty()
    }

    fun getLoginData(): User {
        val gson = Gson()

        return gson.fromJson(
            viewModel.getSharedPreferences().getString("loginData", "").toString(),
            User::class.java
        )
    }


}