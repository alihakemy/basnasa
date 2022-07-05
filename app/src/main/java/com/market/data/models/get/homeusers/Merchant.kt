package com.market.data.models.get.homeusers


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class Merchant(
    @SerializedName("cats")
    var cats: List<String>?,
    @SerializedName("distance")
    var distance: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("favaurite")
    var favaurite: Boolean?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image_panner")
    var imagePanner: List<String>?,
    @SerializedName("imagePath")
    var imagePath: String?,
    @SerializedName("lat")
    var lat: String?,
    @SerializedName("long")
    var long: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("rate")
    var rate: Int?,
    @SerializedName("rate_count")
    var rateCount: Int?,
    @SerializedName("region")
    var region: String?,
    @SerializedName("Roles")
    var roles: String?,
    @SerializedName("shop_name")
    var shopName: String?
)