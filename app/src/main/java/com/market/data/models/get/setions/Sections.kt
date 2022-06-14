package com.market.data.models.get.setions


import com.google.gson.annotations.SerializedName

data class Sections(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)