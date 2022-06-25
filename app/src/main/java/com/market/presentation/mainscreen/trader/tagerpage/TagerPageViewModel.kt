package com.market.presentation.mainscreen.trader.tagerpage

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.tagerprofile.TagerProfile
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagerPageViewModel @Inject constructor(
    private val repoImp: UserRepoImp,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    val results: MutableLiveData<ResultState<TagerProfile>> =
        MutableLiveData<ResultState<TagerProfile>>()


    fun logOut(){
        sharedPreferences.edit().putString("loginData", "").commit()
    }



   fun getTagerProfile(

    ) {


        viewModelScope.launch {

            results.postValue(
                repoImp.getTagerProfiles(
                    sharedPreferences.getString("latitude", "29").toString(),
                    sharedPreferences.getString("longitude", "48").toString()
                )
            )


        }

    }


}