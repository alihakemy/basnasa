package com.market.data.models.get.terms


import com.google.gson.annotations.SerializedName

data class TermsCondtionModel(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)