package com.market.data.models.get.productdetails


import com.google.gson.annotations.SerializedName

data class Rate(
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rate")
    val rate: Int?,
    @SerializedName("user_id")
    val userId: Int?
)