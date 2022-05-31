package com.market.data.models.get.verificationPhone

data class VerificationPhone(
    val `data`: Data,
    val status: Boolean,
    val token: String,
    val message:String ?=null
)