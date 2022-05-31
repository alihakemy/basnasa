package com.market.data.repo

import com.market.data.models.get.OnBoardingGet
import com.market.data.services.OnBoardingServices
import com.market.utils.ResultState
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(private val onBoardingServices: OnBoardingServices) {


    suspend fun getOnBoarding(): ResultState<OnBoardingGet> {

        return try {

            ResultState.Success(onBoardingServices.getOnBoarding())
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage.toString())
        }

    }


}