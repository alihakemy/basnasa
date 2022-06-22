package com.market.data.models.get.tagerprofile


import com.google.gson.annotations.SerializedName

data class TagerProfile(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)