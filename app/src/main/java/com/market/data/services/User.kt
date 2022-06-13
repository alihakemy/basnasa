package com.market.data.services

import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.homeusers.HomeUser
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.search.SearchResults
import retrofit2.http.*

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


    @POST("/api/rate")
    suspend fun addComment(@Header("Authorization") token: String ,@Body map:HashMap<String,String>
    ):DefaultResponse

    @POST("/api/rate/{CommentId}")
    suspend fun editComment(@Header("Authorization") token: String, @Body map:HashMap<String,String>,
                            @Path("CommentId") productId: Int
    ):DefaultResponse

    @DELETE("/api/rate/{productId}")
    suspend fun  deleteComment(@Header("Authorization") token: String ,@Path("productId") productId:String ,
    ):DefaultResponse


}