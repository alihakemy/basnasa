package com.market.data.models.get.offers


import com.google.gson.annotations.SerializedName

data class Offers(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)