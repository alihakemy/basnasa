package com.market.data.models.get.popups


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class popups(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("status")
    var status: String?
)