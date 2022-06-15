package com.market.presentation.mainscreen.user.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.fav.Favourites
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TOKEN = "token"

@HiltViewModel
class FavProductViewModel @Inject constructor(
    private val userRepoImp: UserRepoImp,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val results: MutableLiveData<ResultState<Favourites>> =
        MutableLiveData<ResultState<Favourites>>()


    fun getFav(token: String,lat:String,long:String) {


        viewModelScope.launch {

            results.postValue(userRepoImp.getFav(token,lat,long))


        }
    }
    fun getFav(token: String,lat:String,long:String,id:String) {


        viewModelScope.launch {

            results.postValue(userRepoImp.getFav(token,lat,long,id))


        }
    }


}