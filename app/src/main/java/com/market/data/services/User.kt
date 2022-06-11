package com.market.data.services

import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.homeusers.HomeUser
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface User {


    @GET("api/favourites")
    suspend fun getFavourites(@Header("Authorization") token: String):Favourites


    @GET("api/mainPage")
    suspend fun getUserHomeScreen(@Query("latitude")latitude:String,
                                  @Query("longitude")longitude:String ):HomeUser


}