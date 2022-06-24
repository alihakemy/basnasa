package com.market.data.services

import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.fav.Favourites
import com.market.data.models.get.homeusers.HomeUser
import com.market.data.models.get.links.SocialLinks
import com.market.data.models.get.offers.Offers
import com.market.data.models.get.paymentPackages.PaymentPackages
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.search.SearchResults
import com.market.data.models.get.setions.Sections
import com.market.data.models.get.tagerdetails.TagerDetails
import com.market.data.models.get.tagerprofile.TagerProfile
import com.market.data.models.get.tagetcomplet.TagetCompleteData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface apis {


    @GET("api/favourites")
    suspend fun getFavourites(
        @Header("Authorization") token: String,
        @Query("latitude") lat: String,
        @Query("longitude") long: String
    ): Favourites

    @GET("api/favourites")
    suspend fun getFavourites(
        @Header("Authorization") token: String,
        @Query("latitude") lat: String,
        @Query("longitude") long: String,
        @Query("id") subId: String
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

    @POST("/api/FavouriteAd")
    suspend fun addFav(@Body map: HashMap<String, String>)

    @DELETE("/api/deleteFavouriteAd/{merchant}")
    suspend fun deleteFav(
        @Path("merchant") productId: String,
    ): DefaultResponse


    @GET("/api/merchant/{tagerId}")
    suspend fun getTagerDetails(
        @Path("tagerId") productId: String,
        @Query("cat_id") cat_id: String,


        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): TagerDetails

    @GET("/api/merchant/{tagerId}")
    suspend fun getTagerDetails(
        @Path("tagerId") productId: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): TagerDetails


    @GET("/api/package")
    suspend fun getPaymentPackages(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): PaymentPackages


    @GET("/api/merchant_profile")
    suspend fun getTagerProfile(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): TagerProfile


    @POST("/api/assign_package")
    suspend fun getBuyPackage(@Body packageId: HashMap<String, String>): DefaultResponse


    @Multipart
    @POST("/api/add_product")
    suspend fun addProduct(
        @Part("category_id") category_id: RequestBody,
        @Part("mainprice") mainprice: RequestBody,
        @Part("discount") discount: RequestBody,
        @Part("stoke") stoke: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part files: List<MultipartBody.Part>,
        @Part("currecny") long: RequestBody,
        @Part("content") about: RequestBody,


        ): DefaultResponse


    @Multipart
    @PUT("/api/edit_product/{productId}")
    suspend fun editProduct(
        @Path("productId") productId: String,
        @Part("category_id") category_id: RequestBody,
        @Part("mainprice") mainprice: RequestBody,
        @Part("discount") discount: RequestBody,
        @Part("stoke") stoke: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part files: List<MultipartBody.Part>,
        @Part("currecny") long: RequestBody,
        @Part("about") about: RequestBody,


        ): DefaultResponse

    @DELETE("/api/delete/product/{productId}")
    suspend fun removeProduct(
        @Path("productId") productId: String,

    ): DefaultResponse


    @GET("/api/social")
    suspend fun getLinks(): SocialLinks

}