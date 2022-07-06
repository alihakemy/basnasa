package com.market.data.models.get.currency


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class Currency(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Boolean?
)