package com.market.data.models.get.homeusers


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class Currency(
    @SerializedName("code")
    var code: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("imagePath")
    var imagePath: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("user_id")
    var userId: Int?
)