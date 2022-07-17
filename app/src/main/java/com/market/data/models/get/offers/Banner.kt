package com.market.data.models.get.offers


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("imagePath")
    var imagePath: String?,
    @SerializedName("typeDirection")
    var typeDirection:String?,
    @SerializedName("showNumber")
    var showNumber:String?
)