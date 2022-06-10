package com.market.presentation.bases.methods

import com.market.data.models.get.User
import com.market.data.models.get.login.LoginResponse

interface BaseMethods {
    fun checkIsLogin(): Boolean

    fun getLoginData():LoginResponse

    fun getLocation(): Boolean
    fun getLatLong(): Pair<String, String>


    fun storeLoginData(user: LoginResponse)

}