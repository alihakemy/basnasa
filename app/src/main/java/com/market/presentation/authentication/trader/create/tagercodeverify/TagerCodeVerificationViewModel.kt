package com.market.presentation.authentication.trader.create.tagercodeverify

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.market.data.models.SendVerificationPhone
import com.market.data.models.get.User
import com.market.data.models.get.verificationPhone.VerificationPhone
import com.market.data.repo.AuthenticationRepository
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagerCodeVerificationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val sharedPreferences: SharedPreferences
) :ViewModel() {


    fun verificationPhone(verificationPhone: SendVerificationPhone): LiveData<ResultState<VerificationPhone>> {
        val registerResults: MutableLiveData<ResultState<VerificationPhone>> = MutableLiveData()

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