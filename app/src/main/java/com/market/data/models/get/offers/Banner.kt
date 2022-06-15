package com.market.data.models.get.offers


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imagePath")
    val imagePath: String?
)