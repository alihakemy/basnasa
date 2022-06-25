package com.market.data.models.get.tagerprofile


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Merchant(
    @SerializedName("all_products")
    val allProducts: Int?,
    @SerializedName("bannerPath")
    val bannerPath: String?,
    @SerializedName("content")
    val content: String?,
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
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("long")
    val long: Double?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("pending_products")
    val pendingProducts: Int?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("rate_count")
    val rateCount: Int?,
    @SerializedName("snapchat_link")
    val snapchatLink: String?,
    @SerializedName("text_order")
    val textOrder: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("whatsapp_link")
    val whatsappLink: String?,

    @SerializedName("packege_count")
    val packageCount: Int?,


    ):Parcelable