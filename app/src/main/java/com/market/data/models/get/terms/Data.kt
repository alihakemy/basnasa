package com.market.data.models.get.terms


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("descrption")
    val descrption: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)