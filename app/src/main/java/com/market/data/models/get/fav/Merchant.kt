package com.market.data.models.get.fav


import com.google.gson.annotations.SerializedName

data class Merchant(
    @SerializedName("cats")
    val cats: List<String>?,
    @SerializedName("distance")
    val distance: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("favaurite")
    val favaurite: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imagePath")
    val imagePath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("rate")
    val rate: Int?,
    @SerializedName("rate_count")
    val rateCount: Int?,
    @SerializedName("Roles")
    val roles: String?,
    @SerializedName("shop_name")
    val shopName: String?
)