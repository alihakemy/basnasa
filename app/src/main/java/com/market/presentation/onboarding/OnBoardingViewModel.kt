package com.market.presentation.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.OnBoardingGet
import com.market.data.repo.OnBoardingRepository
import com.market.data.services.OnBoardingServices
import com.market.base.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val onBoardingRepository: OnBoardingRepository) :
    ViewModel() {

        val onBoardingResult :MutableLiveData<ResultState<OnBoardingGet>> =MutableLiveData<ResultState<OnBoardingGet>>().also {
            getOnBoarding()
        }



        private fun  getOnBoarding(){

            viewModelScope.launch(Dispatchers.IO){
                onBoardingResult.postValue(onBoardingRepository.getOnBoarding())

            }

        }


}