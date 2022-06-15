package com.market.data.models.get.fav


import com.google.gson.annotations.SerializedName

data class SubCategory(
    @SerializedName("color")
    val color: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imagePath")
    val imagePath: String?,
    @SerializedName("image_path_hidden")
    val imagePathHidden: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("parent_id")
    val parentId: String?
)