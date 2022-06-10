package com.market.data.repo

import android.util.Log
import com.market.data.models.get.fav.Favourites
import com.market.data.services.OnBoardingServices
import com.market.data.services.User
import com.market.utils.ResultState
import javax.inject.Inject

class UserRepoImp @Inject constructor(private val userService: User) {

    private fun getToken(token: String): String = "Bearer $token"

    suspend fun getFav(token: String): ResultState<Favourites> {

        return try {
            Log.e("Calledssss", token)

            val result = userService.getFavourites(getToken(token))
            Log.e("Called", "Calleds$result")
           ResultState.Success(result)
        } catch (e: Exception) {
            Log.e("Called", "Calleds${e.localizedMessage}")


            ResultState.Error(e.localizedMessage.toString())

        }

    }

}