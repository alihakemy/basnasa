package com.market.data.models.get.notification


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("date")
    val date: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("title")
    val title: String?
)