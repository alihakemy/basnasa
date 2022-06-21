package com.market.data.models.get


import com.google.gson.annotations.SerializedName

data class PaymentMethodId(
    @SerializedName("PaymentMethods")
    val paymentMethods: List<PaymentMethod>?
)