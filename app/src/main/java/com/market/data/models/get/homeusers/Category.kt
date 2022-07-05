package com.market.data.models.get.homeusers


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class Category(
    @SerializedName("color")
    var color: String?,
    @SerializedName("content")
    var content: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("imagePath")
    var imagePath: String?,
    @SerializedName("image_path_hidden")
    var imagePathHidden: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("parent_id")
    var parentId: String?
)