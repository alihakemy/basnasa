package com.market.presentation.mainscreen.user.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.homeusers.HomeUser
import com.market.data.models.get.links.SocialLinks
import com.market.data.models.get.popups.popups
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepoImp: UserRepoImp) : ViewModel() {


    val results: MutableLiveData<ResultState<HomeUser>> =
        MutableLiveData<ResultState<HomeUser>>()
    val popupss:MutableLiveData<ResultState<popups>> =MutableLiveData<ResultState<popups>>()

    init {
        getPopups()
    }

    fun getHomeScreen(
        latitude: String,
        longitude: String
    ) {

        viewModelScope.launch {

            results.postValue(userRepoImp.getUserHomeScreen(latitude, longitude))


        }
    }

    fun getLinks(): MutableLiveData<ResultState<SocialLinks>> {
        val data =MutableLiveData<ResultState<SocialLinks>>()
        viewModelScope.launch {

            data.postValue( userRepoImp.getLinksSocial())

        }
        return data
    }

    fun getPopups() {
        viewModelScope.launch {

           popupss.postValue( userRepoImp.getpopups())

        }
    }



}