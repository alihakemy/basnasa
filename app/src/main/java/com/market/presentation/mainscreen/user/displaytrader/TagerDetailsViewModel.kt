package com.market.presentation.mainscreen.user.displaytrader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.tagerdetails.TagerDetails
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagerDetailsViewModel @Inject constructor(val repoImp: UserRepoImp):ViewModel(){

    val results: MutableLiveData<ResultState<TagerDetails>> = MutableLiveData()



    fun getProductDetails(tagerId: String, latitude: String,
                          longitude: String) {

        viewModelScope.launch {
            val response = repoImp.TagerDetails(tagerId,latitude,longitude)

            results.postValue(response)

        }


    }

    fun getProductDetails(catId:String,tagerId: String, latitude: String,
                          longitude: String) {

        viewModelScope.launch {
            val response = repoImp.TagerDetails(catId,tagerId,latitude,longitude)
            results.postValue(response)

        }


    }

    fun addComment(token:String, tagerId:String, rate: Float, comment:String, latitude: String,
                   longitude: String){
        viewModelScope.launch {
            val map :HashMap<String,String> = HashMap()
            map["tager_id"]=tagerId
            map["rate"]=rate.toString()
            map["comment"]=comment
            val response = repoImp.addComments(map,token)

            when(response){
                is ResultState.Success<DefaultResponse> ->{

                }
                else ->{

                }
            }
            val responses = repoImp.TagerDetails(tagerId,latitude,longitude)
            results.postValue(responses)


        }


    }


    fun deleteComment(token:String, ProductId:String){
        viewModelScope.launch {

            repoImp.deleteComments(ProductId,token)




        }


    }

    fun editeComment(token:String, tagerId:String, rate: Float, comment:String, commentId: Int
                     , latitude: String,
                     longitude: String){
        viewModelScope.launch {

            viewModelScope.launch {
                val map :HashMap<String,String> = HashMap()
                map["tager_id"]=tagerId
                map["rate"]=rate.toString()
                map["comment"]=comment

                val response = repoImp.editeComments(map,token,commentId)

                when(response){
                    is ResultState.Success<DefaultResponse> ->{

                    }
                    else ->{

                    }
                }
                val responses = repoImp.TagerDetails(tagerId,latitude,longitude)
                results.postValue(responses)


            }



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