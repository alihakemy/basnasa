package com.market.data.models.get.homeusers


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class HomeUser(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("status")
    var status: String?
)