package com.market.data.di

import android.content.SharedPreferences
import com.google.gson.Gson
import com.market.data.models.get.login.LoginResponse
import okhttp3.Interceptor
import okhttp3.Response

class Interceptors(
    private val token: SharedPreferences,
    private val lang:String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "Bearer ${getToken(sharedPreferences = token)}")
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("lang",lang)
            .build()
        return chain.proceed(request)
    }

    private fun getToken(sharedPreferences: SharedPreferences): String {

        val gson = Gson()

        val loginresponse = gson.fromJson(
            sharedPreferences.getString("loginData", "").toString(),
            LoginResponse::class.java
        )


        return loginresponse?.data?.token ?: sharedPreferences.getString("token", "").toString()

    }
}