package com.market.presentation.onboarding

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.OnBoardingGet
import com.market.data.repo.OnBoardingRepository
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
const val ONBOARD="Onboard"
@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val onBoardingRepository: OnBoardingRepository,
private val sharedPreferences: SharedPreferences) :
    ViewModel() {


    fun skipOnboard(){
        sharedPreferences.edit().putString(ONBOARD,"done").commit()
    }

        val onBoardingResult :MutableLiveData<ResultState<OnBoardingGet>> =MutableLiveData<ResultState<OnBoardingGet>>().also {
            getOnBoarding()
        }



        private fun  getOnBoarding(){

            viewModelScope.launch(Dispatchers.IO){
                onBoardingResult.postValue(onBoardingRepository.getOnBoarding())

            }

        }


}