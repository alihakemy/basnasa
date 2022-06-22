package com.market.data.models.get.tagerprofile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("active")
    val active: List<ProductItem>?,
    @SerializedName("merchant")
    val merchant: Merchant?,
    @SerializedName("pending")
    val pending: List<ProductItem>?,
    @SerializedName("reject")
    val reject: List<ProductItem>?
)