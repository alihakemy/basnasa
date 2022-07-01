package com.market.presentation.bases

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.market.data.models.get.User
import com.market.data.models.get.login.LoginResponse
import com.market.presentation.bases.methods.BaseMethods
import com.market.presentation.bases.methods.LoginData


abstract class BaseActivity : AppCompatActivity() {

    private val viewModel: BaseViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


    }

    fun checkOnboard(): Boolean {
        return viewModel.checkOnboard()
    }

    fun checkIsLogin(): Boolean {
        return viewModel.checkIsLogin()
    }

    fun getLoginData():LoginResponse {
        return viewModel.getLoginData()
    }

    fun getLocation(): Boolean {
        return viewModel.getLocation()
    }

    fun getLatLong(): Pair<String, String> {
        return viewModel.getLatLong()
    }

    fun storeLoginData(user: LoginResponse){
        viewModel.storeLoginData(user)
    }


}