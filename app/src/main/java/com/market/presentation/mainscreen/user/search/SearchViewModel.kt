package com.market.presentation.mainscreen.user.search

import android.util.Log
import androidx.lifecycle.*
import com.market.data.models.get.search.SearchResults
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repoImp: UserRepoImp,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val results: MutableLiveData<ResultState<SearchResults>> = MutableLiveData()




  fun performSearch(string: String,lat:String,long:String) {

        viewModelScope.launch {
            val response = repoImp.search(string,lat,long)
            results.postValue(response)

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