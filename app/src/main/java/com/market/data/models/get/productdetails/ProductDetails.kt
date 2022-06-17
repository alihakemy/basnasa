package com.market.data.models.get.productdetails


import com.google.gson.annotations.SerializedName

data class ProductDetails(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)