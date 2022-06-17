package com.market.data.models.get.productdetails


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("products")
    val products: Products?,
    @SerializedName("rates")
    val rates: List<Rate>?
)