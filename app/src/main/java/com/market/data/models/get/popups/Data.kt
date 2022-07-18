package com.market.data.models.get.popups


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class Data(
    @SerializedName("slider")
    var slider: List<Slider>?
)