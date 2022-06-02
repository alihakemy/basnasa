package com.market.presentation.bases.methods

import com.market.data.models.get.User

interface BaseMethods :BaseLocationMethod {
    fun checkIsLogin(): Boolean

    fun getLoginData(): User



}