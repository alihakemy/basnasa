package com.market.data.models.get.tagerprofile


import com.google.gson.annotations.SerializedName

data class Merchant(
    @SerializedName("all_products")
    val allProducts: Int?,
    @SerializedName("bannerPath")
    val bannerPath: String?,
    @SerializedName("content")
    val content: Any?,
    @SerializedName("discount_text")
    val discountText: String?,
    @SerializedName("distance")
    val distance: String?,
    @SerializedName("facebook_link")
    val facebookLink: Any?,
    @SerializedName("favaurite")
    val favaurite: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imagePath")
    val imagePath: String?,
    @SerializedName("instagram_link")
    val instagramLink: Any?,
    @SerializedName("lat")
    val lat: Any?,
    @SerializedName("long")
    val long: Any?,
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
    val snapchatLink: Any?,
    @SerializedName("text_order")
    val textOrder: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("whatsapp_link")
    val whatsappLink: Any?,

    @SerializedName("packege_count")
    val packageCount: Int?,


    )