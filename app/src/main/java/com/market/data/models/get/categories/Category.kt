package com.market.data.models.get.categories

data class Category(
    val id: Int,
    val imagePath: String,
    val name: String,
    val parent_id: String
)