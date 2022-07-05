package com.market.data.models.get.homeusers


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Banner(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("imagePath")
    var imagePath: String?
)