package com.market.data.services

import com.market.data.models.get.fav.Favourites
import retrofit2.http.GET
import retrofit2.http.Header

interface User {


    @GET("api/favourites")
    suspend fun getFavourites(@Header("Authorization") token: String):Favourites

}