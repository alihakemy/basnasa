package com.market.data.models.get.homeusers


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class Data(
    @SerializedName("banner")
    var banner: List<Banner>?,
    @SerializedName("categories")
    var categories: List<Category>?,
    @SerializedName("merchants")
    var merchants: List<Merchant>?,
    @SerializedName("products")
    var products: List<Product>?,
    @SerializedName("slider")
    var slider: List<Slider>?
)