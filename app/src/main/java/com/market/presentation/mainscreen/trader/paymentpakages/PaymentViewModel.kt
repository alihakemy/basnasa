package com.market.presentation.mainscreen.trader.paymentpakages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.paymentPackages.PaymentPackages
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(val repoImp: UserRepoImp):ViewModel() {

    val results: MutableLiveData<ResultState<PaymentPackages>> = MutableLiveData()



    fun getPaymentPackages(latitude: String,
                          longitude: String) {

        viewModelScope.launch {
            val response = repoImp.getPaymentPackages(latitude,longitude)
            results.postValue(response)

        }


    }
}