package com.market.presentation.mainscreen.trader.addproduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.categories.Categories
import com.market.data.repo.AuthenticationRepository
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private  val  authenticationRepository: AuthenticationRepository):ViewModel() {

    val categories : MutableLiveData<ResultState<Categories>> =
        MutableLiveData<ResultState<Categories>>()
        .also {
            getCategories()
        }


    private fun getCategories(){

        viewModelScope.launch(Dispatchers.IO){

            categories.postValue(authenticationRepository.getCategories())

        }
    }


}