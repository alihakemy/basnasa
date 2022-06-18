package com.market.data.models.get.tagerdetails


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("imagePath")
    val imagePath: String?
)