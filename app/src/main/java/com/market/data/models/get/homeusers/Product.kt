package com.market.data.models.get.homeusers


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class Product(
    @SerializedName("cats")
    var cats: List<String>?,
    @SerializedName("content")
    var content: String?,
    @SerializedName("currency")
    var currency: Currency?,
    @SerializedName("discount")
    var discount: String?,
    @SerializedName("distance")
    var distance: String?,
    @SerializedName("favourite")
    var favourite: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image_path")
    var imagePath: String?,
    @SerializedName("images")
    var images: List<Any>?,
    @SerializedName("mainprice")
    var mainprice: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("prefitPrice")
    var prefitPrice: String?,
    @SerializedName("rate")
    var rate: Any?,
    @SerializedName("rate_count")
    var rateCount: Int?,
    @SerializedName("shop_name")
    var shopName: String?,
    @SerializedName("tager")
    var tager: String?,
    @SerializedName("tager_image")
    var tagerImage: String?,
    @SerializedName("tager_phone")
    var tagerPhone: String?
)