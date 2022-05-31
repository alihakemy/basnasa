package com.market.data.services

import com.market.data.models.get.OnBoardingGet
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface OnBoardingServices {

    @GET("api/landing_board")
    suspend fun getOnBoarding( ):OnBoardingGet
}