package com.market.presentation.mainscreen.user.updateprofile

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.market.data.models.get.login.LoginResponse
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UpdateUserProfileViewModel @Inject constructor(
    private val repoImp: UserRepoImp,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun logOut(){
        sharedPreferences.edit().putString("loginData", "").commit()
    }

    val result  :MutableLiveData<ResultState<LoginResponse>> = MutableLiveData()

    fun updateUserProfile(userName:String,phone:String,email:String){
        val map =HashMap<String,String>()
        map["_method"] = "put"
        map["name"] = userName
        map["email"] = email
        map["phone"] = phone

        viewModelScope.launch {
            result.postValue( repoImp.updateUserProfile(map))


        }

    }

    fun storeLogin(user: LoginResponse?){

        val gson = Gson()
        val jsonObject = gson.toJson(user)
        sharedPreferences.edit().putString("loginData",jsonObject).commit()

    }


}