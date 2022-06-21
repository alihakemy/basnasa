package com.market.data.models.get.paymentPackages


import com.google.gson.annotations.SerializedName


data class PaymentPackages(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)