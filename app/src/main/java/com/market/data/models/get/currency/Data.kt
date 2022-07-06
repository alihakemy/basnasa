package com.market.data.models.get.currency


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class Data(
    @SerializedName("paymentMethod")
    var paymentMethod: List<PaymentMethod>?
)