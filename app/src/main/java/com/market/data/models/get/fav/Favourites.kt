package com.market.data.models.get.fav


import com.google.gson.annotations.SerializedName

data class Favourites(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)