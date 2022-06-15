package com.market.data.di

import okhttp3.Interceptor
import okhttp3.Response

class Interceptors(
    private val token: String,
    private val lang:String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("lang",lang)
            .build()
        return chain.proceed(request)
    }
}