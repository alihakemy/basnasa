package com.market.presentation.bases.methods

import android.content.SharedPreferences
import com.google.gson.Gson
import com.market.data.models.get.User
import com.market.data.models.get.categories.Category
import com.market.data.models.get.login.Data
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

        return if (!getSharedPreferences().getString("loginData", "").isNullOrEmpty()) {
            gson.fromJson(
                getSharedPreferences().getString("loginData", "").toString(),
                LoginResponse::class.java
            )
        } else {
            LoginResponse(data = Data("","",
                User("clients", emptyList(),"",-1,"","","",
                0,0,"",
                "","","","","")),""
            ,"","")
        }

    }

    override fun getLatLong(): Pair<String, String> {

        return Pair(
            getSharedPreferences().getString("latitude", "29.3117") ?: "29.3117",
            getSharedPreferences().getString("longitude", "47.4818") ?: "47.4818"
        )

    }

    override fun storeLoginData(user: LoginResponse) {
        val gson = Gson()
        val jsonObject = gson.toJson(user)
        sharedPreferences.edit().putString("loginData", jsonObject).commit()
    }
}