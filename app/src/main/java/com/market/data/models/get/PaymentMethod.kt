package com.market.data.models.get


import com.google.gson.annotations.SerializedName

data class PaymentMethod(
    @SerializedName("CurrencyIso")
    val currencyIso: String?,
    @SerializedName("ImageUrl")
    val imageUrl: String?,
    @SerializedName("IsDirectPayment")
    val isDirectPayment: Boolean?,
    @SerializedName("PaymentMethodAr")
    val paymentMethodAr: String?,
    @SerializedName("PaymentMethodCode")
    val paymentMethodCode: String?,
    @SerializedName("PaymentMethodEn")
    val paymentMethodEn: String?,
    @SerializedName("PaymentMethodId")
    val paymentMethodId: Int?,
    @SerializedName("ServiceCharge")
    val serviceCharge: Double?,
    @SerializedName("TotalAmount")
    val totalAmount: Double?
)