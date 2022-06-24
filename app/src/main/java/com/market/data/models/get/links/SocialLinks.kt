package com.market.data.models.get.links


import com.google.gson.annotations.SerializedName

data class SocialLinks(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)