package com.market.presentation.bases

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.market.data.models.get.User
import com.market.presentation.bases.methods.BaseMethods
import com.market.presentation.bases.methods.LoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {

    private fun getSharedPreferences() = sharedPreferences
   private  var loginData: LoginData = LoginData(getSharedPreferences())


    private fun checkIsLogin(baseMethods: BaseMethods): Boolean {
        return baseMethods.checkIsLogin()
    }

    private fun getLocation(baseMethods: BaseMethods): Boolean {
        return baseMethods.getLocation()
    }

    private fun getLoginData(baseMethods: BaseMethods): User {
        return baseMethods.getLoginData()
    }

    private fun getLatLong(baseMethods: BaseMethods): Pair<String, String> {
        return baseMethods.getLatLong()
    }
    private fun storeLoginData(baseMethods: BaseMethods,user: User){
        baseMethods.storeLoginData(user)
    }

    fun checkIsLogin() = checkIsLogin(loginData)
    fun getLocation() = getLocation(loginData)
    fun getLoginData() = getLoginData(loginData)
    fun getLatLong() = getLatLong(loginData)
    fun storeLoginData(user: User)=storeLoginData(loginData,user)

}