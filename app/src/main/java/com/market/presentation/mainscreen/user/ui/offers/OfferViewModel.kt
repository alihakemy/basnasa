package com.market.presentation.mainscreen.user.ui.offers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.offers.Offers
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OfferViewModel @Inject constructor(val repoImp: UserRepoImp) : ViewModel() {


    val results:MutableLiveData<ResultState<Offers>> = MutableLiveData()
    fun getOffers(latitude: String, longitude: String){
        viewModelScope.launch {
            results.postValue(repoImp.getOffers(latitude, longitude))

        }
    }

    fun getOffers(latitude: String, longitude: String, id:String){
        viewModelScope.launch {
            results.postValue(repoImp.getOffers(latitude, longitude, id.toString()))

        }
    }
    fun perFormLike(merchant_id:String){
        viewModelScope.launch {
            repoImp.addFav(merchant_id)
        }
    }

    fun perFormUnLike(merchant_id:String){
        viewModelScope.launch {
            repoImp.removeFav(merchant_id)
        }
    }

}