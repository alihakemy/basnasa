package com.market.data.models.get.addComment


import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)