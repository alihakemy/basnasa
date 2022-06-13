package com.market.presentation.mainscreen.user.displayproduct

import androidx.lifecycle.*
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.models.get.search.SearchResults
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(val repoImp: UserRepoImp,
private val savedStateHandle: SavedStateHandle) :ViewModel(){


    val results: MutableLiveData<ResultState<ProductDetails>> = MutableLiveData()



    fun getProductDetails(productId: String) {

        viewModelScope.launch {
            val response = repoImp.productDetails(productId)
            results.postValue(response)

        }


    }
    
    
}