package com.market.data.models.get.setions


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("merchants")
    val merchants: List<Merchant>?,
    @SerializedName("sub_categories")
    val subCategories: List<SubCategory>?
)