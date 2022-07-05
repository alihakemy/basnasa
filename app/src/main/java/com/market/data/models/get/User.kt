package com.market.data.models.get

data class User(
    val Roles: String,
    val cats: List<String>,
    val email: String,
    val id: Int,
    val imagePath: String,
    val name: String,
    val phone: String,
    val rate: Int,
    val status:Int,
    var shop_name: String? ,
    var instagram_link: String?,
    var facebook_link: String?,
    var whatsapp_link: String?,
    var snapchat_link: String?,
    var arrivaltime: String ?
)