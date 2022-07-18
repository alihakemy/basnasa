package com.market.data.models.get.popups


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Slider(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("imagePath")
    var imagePath: String?,
    @SerializedName("showNumber")
    var showNumber: String?,
    @SerializedName("typeDirection")
    var typeDirection: String?
)