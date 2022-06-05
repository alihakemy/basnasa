package com.market.presentation.authentication.user.login

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.market.data.models.SendLogin
import com.market.data.models.get.login.LoginResponse
import com.market.data.models.get.User
import com.market.data.repo.AuthenticationRepository
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private  val  authenticationRepository: AuthenticationRepository,
                                         private val sharedPreferences: SharedPreferences
) :ViewModel() {



    val  loginResults:MutableLiveData<ResultState<LoginResponse>> = MutableLiveData()

   fun loginUser(sendLogin: SendLogin){
        viewModelScope.launch(Dispatchers.IO){

            loginResults.postValue( authenticationRepository.login(sendLogin))


        }
    }


}