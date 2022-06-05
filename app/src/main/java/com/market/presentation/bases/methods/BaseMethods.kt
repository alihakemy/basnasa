package com.market.presentation.bases.methods

import com.market.data.models.get.User

interface BaseMethods {
    fun checkIsLogin(): Boolean

    fun getLoginData(): User

    fun getLocation(): Boolean
    fun getLatLong(): Pair<String, String>


    fun storeLoginData(user: User)

}