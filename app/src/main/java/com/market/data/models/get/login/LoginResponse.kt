package com.market.data.models.get.login

data class LoginResponse(
    val `data`: Data,
    val status: String,
    val message: String? = null,
    val error: String? = null
)