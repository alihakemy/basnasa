package com.market.presentation.authentication.trader.create.traderjoin

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.SendRegister
import com.market.data.models.get.register.RegisterResponse
import com.market.data.repo.AuthenticationRepository
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinAsTraderViewModel @Inject constructor(private  val  authenticationRepository: AuthenticationRepository,
                                                private val sharedPreferences: SharedPreferences
) : ViewModel() {


    fun registerTager(register: SendRegister): LiveData<ResultState<RegisterResponse>> {
        val registerResults: MutableLiveData<ResultState<RegisterResponse>> = MutableLiveData()

        viewModelScope.launch(Dispatchers.IO) {

            registerResults.postValue(authenticationRepository.register(register))


        }
        return registerResults
    }

}