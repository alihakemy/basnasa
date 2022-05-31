package com.market.data.models.get.login

import com.market.data.models.get.User

data class Data(
    val activation_code: String,
    val token: String,
    val user: User
)