package com.market.data.services

import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.homeusers.HomeUser
import com.market.data.models.get.offers.Offers
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.search.SearchResults
import com.market.data.models.get.setions.Sections
import retrofit2.http.*

interface User {


    @GET("api/favourites")
    suspend fun getFavourites(@Header("Authorization") token: String,
                              @Query("latitude")lat:String,
                              @Query("longitude")long :String
                              ): Favourites

    @GET("api/favourites")
    suspend fun getFavourites(@Header("Authorization") token: String,
                              @Query("latitude")lat:String,
                              @Query("longitude")long :String,
                              @Query("id")subId:String
    ): Favourites


    @GET("api/mainPage")
    suspend fun getUserHomeScreen(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
    ): HomeUser


    @GET("api/search/merchants")
    suspend fun getSearch(
        @Query("q") query: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): SearchResults


    @GET("/api/product/{productId}")
    suspend fun getProductDetails(
        @Path("productId") productId: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): ProductDetails


    @POST("/api/rate")
    suspend fun addComment(
        @Header("Authorization") token: String, @Body map: HashMap<String, String>
    ): DefaultResponse

    @POST("/api/rate/{CommentId}")
    suspend fun editComment(
        @Header("Authorization") token: String, @Body map: HashMap<String, String>,
        @Path("CommentId") productId: Int
    ): DefaultResponse

    @DELETE("/api/rate/{productId}")
    suspend fun deleteComment(
        @Header("Authorization") token: String, @Path("productId") productId: String,
    ): DefaultResponse


    @GET("/api/category/{categoriesId}/merchants")
    suspend fun getSectionCategories(
        @Path("categoriesId") categoriesId: String,
        @Header("Authorization") token: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Sections

    @GET("/api/cat/{categoriesId}/sub/{subcategoriesId}/merchants")
    suspend fun getSectionSubCategories(
        @Path("categoriesId") categoriesId: String,
        @Path("subcategoriesId") subcategoriesId: String,

        @Header("Authorization") token: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Sections







    @GET("/api/offers/merchants")
    suspend fun getOffer(

        @Query("lat") latitude: String,
        @Query("long") longitude: String
    ): Offers
    @GET("/api/offers/merchants")
    suspend fun getOffer(

        @Query("lat") latitude: String,
        @Query("long") longitude: String,
        @Query("id") id: String
    ): Offers

}