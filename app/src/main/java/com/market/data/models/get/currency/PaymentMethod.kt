package com.market.data.models.get.currency


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class PaymentMethod(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
)