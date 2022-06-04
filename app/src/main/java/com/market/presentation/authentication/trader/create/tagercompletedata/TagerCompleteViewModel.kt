package com.market.presentation.authentication.trader.create.tagercompletedata

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.SendCompleteJoin
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.login.LoginResponse
import com.market.data.models.get.tagetcomplet.TagetCompleteData
import com.market.data.repo.AuthenticationRepository
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel

class TagerCompleteViewModel @Inject constructor(private  val  authenticationRepository: AuthenticationRepository,
                                                 private val sharedPreferences: SharedPreferences
) :ViewModel() {

    val categories :MutableLiveData<ResultState<Categories>> =MutableLiveData<ResultState<Categories>>()
        .also {
            getCategories()
        }

    val status:MutableLiveData<String> = MutableLiveData()

    private fun getCategories(){

        viewModelScope.launch(Dispatchers.IO){

            categories.postValue(authenticationRepository.getCategories())

        }
    }


      fun uploadStore(sendCompleteJoin: SendCompleteJoin, token:String,image: MultipartBody.Part){


        viewModelScope.launch(Dispatchers.IO){

           when(val result = authenticationRepository.completeJoinTager(sendCompleteJoin,token,image)){

               is ResultState.Success<TagetCompleteData>->{
                   Log.e("REsultALI",result.data.data.toString())
                   status.postValue("Sucess")
               }
               else ->{
                   Log.e("REsultALIERREOR",result.toString())
                   status.postValue(result.toString())
               }

           }
        }





    }




}