package com.market.presentation.bases

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.market.data.models.get.User
import com.market.presentation.bases.methods.BaseMethods


abstract class BaseActivity : AppCompatActivity(), BaseMethods {

    private val viewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    override fun checkIsLogin(): Boolean {
        return !viewModel.getSharedPreferences().getString("loginData", "").toString()
            .isNullOrEmpty()
    }

    override fun getLocation(): Boolean {
        return !viewModel.getSharedPreferences().getString("latitude", "").toString()
            .isNullOrEmpty()
    }

    override fun getLoginData(): User {
        val gson = Gson()

        return gson.fromJson(
            viewModel.getSharedPreferences().getString("loginData", "").toString(),
            User::class.java
        )
    }

    override fun getLatLong(): Pair<String, String> {

        return Pair(viewModel.getSharedPreferences().getString("latitude","0")?:"0",
            viewModel.getSharedPreferences().getString("longitude","0") ?:"0" )

    }


}