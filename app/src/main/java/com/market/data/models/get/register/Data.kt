package com.market.data.models.get.register

import com.market.data.models.get.User

data class Data(
    val activation_code: Int,
    val token: String,
    val user: User
)