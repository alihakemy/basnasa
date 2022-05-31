package com.market.presentation.authentication.user.create

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.market.data.models.SendLogin
import com.market.data.models.SendRegister
import com.market.data.models.get.login.LoginResponse
import com.market.data.models.get.register.RegisterResponse
import com.market.data.repo.AuthenticationRepository
import com.market.base.utils.ResultState
import com.market.data.models.SendVerificationPhone
import com.market.data.models.get.User
import com.market.data.models.get.verificationPhone.VerificationPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    fun registerUser(register: SendRegister): LiveData<ResultState<RegisterResponse>> {
        val registerResults: MutableLiveData<ResultState<RegisterResponse>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {

            registerResults.postValue(authenticationRepository.register(register))


        }
        return registerResults
    }


    fun verificationPhone(verificationPhone: SendVerificationPhone): LiveData< ResultState<VerificationPhone>> {
        val registerResults: MutableLiveData< ResultState<VerificationPhone>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {

            registerResults.postValue(authenticationRepository.verifyCode(verificationPhone))


        }
        return registerResults
    }

    fun storeLogin(user: User){
        val gson = Gson()
        val jsonObject = gson.toJson(user)
        sharedPreferences.edit().putString("loginData",jsonObject).commit()

    }
}