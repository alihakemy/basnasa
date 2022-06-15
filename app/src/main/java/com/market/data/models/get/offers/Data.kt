package com.market.data.models.get.offers


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("banner")
    val banner: List<Banner>?,
    @SerializedName("merchants")
    val merchants: List<Merchant>?,
    @SerializedName("sub_categories")
    val subCategories: List<SubCategory>?
)