package com.market.presentation.mainscreen.trader.showProductDetails

import androidx.lifecycle.*
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModelTager @Inject constructor(val repoImp: UserRepoImp,
private val savedStateHandle: SavedStateHandle) :ViewModel(){


    val results: MutableLiveData<ResultState<ProductDetails>> = MutableLiveData()

    val  resultsRemove: MutableLiveData<ResultState<DefaultResponse>> = MutableLiveData()


    fun getProductDetails(productId: String, latitude: String,
                          longitude: String) {

        viewModelScope.launch {
            val response = repoImp.productDetails(productId,latitude,longitude)
            results.postValue(response)

        }


    }


    fun removeProduct(productId:String){
        viewModelScope.launch {
            val response = repoImp.removeProduct(productId)
            resultsRemove.postValue(response)

        }
    }







}