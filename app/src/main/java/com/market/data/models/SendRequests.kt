package com.market.data.models


data class SendLogin(val email: String, val password: String)


data class SendRegister(
    val name: String, val email: String, val password: String,
    val phone: String, val role: String/* clients - Tager*/,
    val shop_name: String="", val Installed: String
)


data class SendVerificationPhone(val phone: String, val code: String)


data class ResendCode(val phone: String, val type: String)

data class ForgetPassword(val phone: String)


data class ConfirmNewPassword(
    val activationCode: String,
    val password: String, val password_confirmation: String
)


