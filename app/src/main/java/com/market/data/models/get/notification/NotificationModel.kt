package com.market.data.models.get.notification


import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)