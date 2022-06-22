package com.market.presentation.mainscreen.trader.paymentpakages.packagedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPackageViewModel @Inject constructor(private val userRepoImp: UserRepoImp) :ViewModel(){

    val result =MutableLiveData<ResultState<DefaultResponse>>()


  fun subscribePackage(packageId:String, onInvoiceCreated:String){

        viewModelScope.launch {
            result.postValue(
                userRepoImp.getBuyPackage(packageId,onInvoiceCreated)

            )
        }

    }

}