package com.market.data.models.get.tagetcomplet

data class User(
    val Roles: String,
    val cats: List<String>,
    val email: String,
    val id: Int,
    val imagePath: String,
    val name: String,
    val phone: String,
    val rate: Int
)