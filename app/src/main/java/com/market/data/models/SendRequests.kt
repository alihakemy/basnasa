package com.market.data.models

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody


data class SendLogin(val email: String, val password: String,val device_token:String)


data class SendRegister(
    val name: String, val email: String, val password: String,
    val phone: String, val role: String/* clients - Tager*/,
    val shop_name: String = "", val Installed: String
)


data class SendVerificationPhone(val phone: String, val code: String)


data class ResendCode(val phone: String, val type: String)

data class ForgetPassword(val phone: String)


data class ConfirmNewPassword(
    val activationCode: String,
    val password: String, val password_confirmation: String
)


data class SendCompleteJoin(

    val category_id: String,
    val arrivaltime: String,
    val instagram_link: String,
    val facebook_link: String,
    val whatsapp_link: String,
    val snapchat_link: String,
    val lat: String,
    val long: String,
    val about: String,

    val phone: String
)
