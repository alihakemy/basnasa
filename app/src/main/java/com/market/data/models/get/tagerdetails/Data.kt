package com.market.data.models.get.tagerdetails


import com.google.gson.annotations.SerializedName
import com.market.data.models.get.productdetails.Rate

data class Data(
    @SerializedName("categories")
    val categories: ArrayList<Category>?,
    @SerializedName("merchant")
    val merchant: Merchant?,
    @SerializedName("products")
    val products: ArrayList<Product>?,
    @SerializedName("rates")
    val rates: ArrayList<Rate>?
)