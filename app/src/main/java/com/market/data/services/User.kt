package com.market.data.services

import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.homeusers.HomeUser
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.search.SearchResults
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface User {


    @GET("api/favourites")
    suspend fun getFavourites(@Header("Authorization") token: String):Favourites


    @GET("api/mainPage")
    suspend fun getUserHomeScreen(@Query("latitude")latitude:String,
                                  @Query("longitude")longitude:String ):HomeUser


    @GET("api/search/merchants")
    suspend fun getSearch(@Query("q")query:String,
                                  ):SearchResults


    @GET("/api/product/{productId}")
    suspend fun getProductDetails(@Path("productId") productId:String ,
    ):ProductDetails
}