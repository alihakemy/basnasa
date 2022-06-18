package com.market.data.models.get.tagerdetails


import com.google.gson.annotations.SerializedName

data class Merchant(
    @SerializedName("all_products")
    val allProducts: Int?,
    @SerializedName("bannerPath")
    val bannerPath: String?,
    @SerializedName("lat")
    val lat:String,
    @SerializedName("long")
    val long:String,
    @SerializedName("content")
    val content: Any?,
    @SerializedName("discount_text")
    val discountText: String?,
    @SerializedName("distance")
    val distance: String?,
    @SerializedName("facebook_link")
    val facebookLink: String?,
    @SerializedName("favaurite")
    val favaurite: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imagePath")
    val imagePath: String?,
    @SerializedName("instagram_link")
    val instagramLink: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("pending_products")
    val pendingProducts: Int?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("rate")
    val rate: Any?,
    @SerializedName("rate_count")
    val rateCount: Int?,
    @SerializedName("snapchat_link")
    val snapchatLink: String?,
    @SerializedName("text_order")
    val textOrder: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("whatsapp_link")
    val whatsappLink: String?
)