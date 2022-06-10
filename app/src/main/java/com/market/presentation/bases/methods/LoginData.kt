package com.market.presentation.bases.methods

import android.content.SharedPreferences
import com.google.gson.Gson
import com.market.data.models.get.User
import com.market.data.models.get.login.LoginResponse
import com.market.presentation.bases.BaseActivity
import javax.inject.Inject

class LoginData constructor(private val sharedPreferences: SharedPreferences) : BaseMethods {
    fun getSharedPreferences() = sharedPreferences

    override fun checkIsLogin(): Boolean {
        return !getSharedPreferences().getString("loginData", "").toString()
            .isNullOrEmpty()
    }

    override fun getLocation(): Boolean {
        return !getSharedPreferences().getString("latitude", "").toString()
            .isNullOrEmpty()
    }

    override fun getLoginData(): LoginResponse {
        val gson = Gson()

        return gson.fromJson(
            getSharedPreferences().getString("loginData", "").toString(),
            LoginResponse::class.java
        )
    }

    override fun getLatLong(): Pair<String, String> {

        return Pair(
            getSharedPreferences().getString("latitude", "0") ?: "0",
            getSharedPreferences().getString("longitude", "0") ?: "0"
        )

    }

    override fun storeLoginData(user:LoginResponse) {
        val gson = Gson()
        val jsonObject = gson.toJson(user)
        sharedPreferences.edit().putString("loginData",jsonObject).commit()
    }
}