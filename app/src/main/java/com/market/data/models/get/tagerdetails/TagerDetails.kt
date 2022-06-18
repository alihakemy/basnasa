package com.market.data.models.get.tagerdetails


import com.google.gson.annotations.SerializedName

data class TagerDetails(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)