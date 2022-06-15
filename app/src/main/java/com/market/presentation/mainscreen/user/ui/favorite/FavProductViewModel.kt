package com.market.presentation.mainscreen.user.ui.favorite

import android.util.Log
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
        val token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiODY4MzhmMzE4NmEyN2ZiMmE5ZDc1MzQ5MDk1ZGNhMWNhNTYxNzJiNTE0YTFiZGViMjkyMDIyNDliNDI1YzBmZTZiMDNkMzVhYWJkNGViOWYiLCJpYXQiOjE2NTQyODkyOTMsIm5iZiI6MTY1NDI4OTI5MywiZXhwIjoxNjg1ODI1MjkzLCJzdWIiOiI2MTA4Iiwic2NvcGVzIjpbXX0.cji6J0DXhvn-tDu5kgL_5jn5BzbdLZRG_4Pn4dHD-A4AqRnRtA7H9NUuZKfDi5xK49OY5r1Fw7u4G05jVFAu_6Wm_muyo8C7ruSS9fJA4jzO0X6z9jnltjP2z8nocyS_aa5OJU-wKtqmrqMD62U-0C7lz0lLhzKNcKygFY3YErOEmA8EnwNCqp50hYQ-kL37JNXuXNV-9KkqX9oZ92V5uWStzGIvwgZhCau9UFU2JJ1lgOczS2tvdSPENAey8ZBmQ_p8m1daEWH448BlXOKMBrixsZaf_a8QTIXVuE_r94u1yHOxQDPS2xngL8GMZNMuUS_HyLEXsHJ_7CjQ1UeeRvz3naPEwSpuuCPgLODJxep8ubzKK1C6cfwTQWXAnnFLNRrV3agrvg6gKPHMEBrGGr-7gqKh5rQhuCkQNpMi6UWIykvarenw8ZZwp6eJXR890AyAH09lKQhbb2DX2cHj-l3HMQyGuWyRXi0wwESs2jIK4ngDsFm-Is2JGJwZZOWrSHIfWwWIt6o8yBmXU9VD9cVMj5z4pKYkrVzC8vn-JAh2m_N8JPQvRJ7jgSFMoOWISe2WhBYcc-CeysSobbXUVsn5tznWihPnfqtjLueBzWhFjWsjxeeH-r96B8Um2s3hc1Onzlret58F5cxjZaEoFgR7BJ5IC3hGO7hqUvjAj1s"


        viewModelScope.launch {

            results.postValue(userRepoImp.getFav(token,lat,long))


        }
    }


}