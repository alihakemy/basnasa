package com.market.data.models.get.paymentPackages


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("packages")
    val packages: ArrayList<Package>?
)