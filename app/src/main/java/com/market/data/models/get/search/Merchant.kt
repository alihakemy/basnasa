package com.market.data.models.get.search

data class Merchant(
    val Roles: String,
    val cats: List<String>,
    val distance: String,
    val email: String,
    var favaurite: Boolean,
    val id: Int,
    val imagePath: String,
    val name: String,
    val phone: String,
    val rate: Int,
    val rate_count: Int,
    val shop_name: String
)