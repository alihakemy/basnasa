package com.market.data.models.get.tagerprofile


import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("content")
    val content: String?,
    @SerializedName("discount")
    val discount: String?,
    @SerializedName("distance")
    val distance: String?,
    @SerializedName("favourite")
    val favourite: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("images")
    val images: List<Any>?,
    @SerializedName("mainprice")
    val mainprice: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("prefitPrice")
    val prefitPrice: String?,
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("rate_count")
    val rateCount: Int?,
    @SerializedName("shop_name")
    val shopName: String?,
    @SerializedName("tager")
    val tager: String?,
    @SerializedName("tager_image")
    val tagerImage: String?,
    @SerializedName("tager_phone")
    val tagerPhone: String?
)