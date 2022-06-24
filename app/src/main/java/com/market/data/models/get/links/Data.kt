package com.market.data.models.get.links


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("facebook")
    val facebook: String?,
    @SerializedName("instgrame")
    val instgrame: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("snapchat")
    val snapchat: String?,
    @SerializedName("twitter")
    val twitter: String?
)