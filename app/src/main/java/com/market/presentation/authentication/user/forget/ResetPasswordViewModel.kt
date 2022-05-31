package com.market.presentation.authentication.user.forget

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.utils.ResultState
import com.market.data.models.ConfirmNewPassword
import com.market.data.models.ForgetPassword
import com.market.data.models.get.forgetpassword.GetForgetPassword
import com.market.data.models.get.getconfirenewpassword.GetConfirmNewPassword
import com.market.data.repo.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val sharedPreferences: SharedPreferences

) :ViewModel() {

    fun forgetPassword(forgetPassword: ForgetPassword): LiveData<ResultState<GetForgetPassword>> {
        val registerResults: MutableLiveData<ResultState<GetForgetPassword>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {

            registerResults.postValue(authenticationRepository.forgetPassword(forgetPassword))


        }
        return registerResults
    }



    fun confirmNewPassword(confirmNewPassword: ConfirmNewPassword): LiveData<ResultState<GetConfirmNewPassword>> {
        val registerResults: MutableLiveData<ResultState<GetConfirmNewPassword>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {

            registerResults.postValue(authenticationRepository.confirmNewPassword(confirmNewPassword))


        }
        return registerResults
    }
}