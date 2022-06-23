package com.market.data.di

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.market.BuildConfig
import com.market.data.models.get.login.LoginResponse
import okhttp3.Interceptor
import okhttp3.Response

class Interceptors(
    private val sharedPreferences: SharedPreferences,
    private val lang:String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        Log.e("TokeALI",getToken(sharedPreferences))

        request = request.newBuilder()
            .header("Authorization", "Bearer ${getToken(sharedPreferences = sharedPreferences)}")
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

        return if(BuildConfig.DEBUG){
            loginresponse?.data?.token ?: sharedPreferences.getString("token", "").toString()

//            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiYzMzM2FhNTc2OWE5ZjQ2YjQwNzAzMzg3NTMzZmU2NTY1ZmFiZGQyODg1Y2Y3NzA1M2U1Mjk5NDA4Y2Q1NjA3ODNmYTBmZmYwMTU3MjA0NzAiLCJpYXQiOjE2NTU4NDM3NzAsIm5iZiI6MTY1NTg0Mzc3MCwiZXhwIjoxNjg3Mzc5NzcwLCJzdWIiOiI2MjE3Iiwic2NvcGVzIjpbXX0.DVHW471Co2RTVs4SSZxxOmMhTOGpqr6Q9dsy_tSuaJdk67k1JyDk130b6e6n5srKALBZiBH5RXQCw84zkq_-WATb6Z8Z6uWR88quLTVwID-mfE-boExS09r8Jc7b-FsiaicRklpmmJEoQEzqcHaF0jTik9cKhMBE-CZyl6D57SSpNqAnsxkbT2ca9UizZujG4ExWuw0UV32J7AiAnAfRjaRm83jF1WTEehjXJrOZR_mt-Z6KECT7egRzd_sZDoPtkZkm-TEU3nn4quN22Of3LsOmbZHu74nYo8-3d42AATscCy7QJtFdNEu7YcvdVK8Gwi7ufQfXr0tByoimKO1DArC7tuJm4nPpkZXV_ogHzyGlsf5-nyAmzgkhks5V3GggqA00WZtWsqUGz6dNMevVPrm1tDE9uktrj4bRHRKHyzyPevKdFjy99oG5uo6tytO3T0el5ekAuiaY13bVuEpIbEJsqW2bj0LvBq1b440Iba5K9TT_VQAe9LKpzmB8Yf2hoRboHd3CfC9EFpbWN7V6toGd7_a9OvMTkWi_PAoYa9PUQLfYPyU3rEoEiGxFZUT2RtVvddt0wurtO2oW1EXEz7GCtFsK44lqPUwEyYj9AByPDQhG1WRCOzeueebcUkcnyTB5DDvHzS5HrPAGg0qmdbZAfjLCilgRinp44J7ZtHs"
        }else {
            loginresponse?.data?.token ?: sharedPreferences.getString("token", "").toString()

        }


    }
}