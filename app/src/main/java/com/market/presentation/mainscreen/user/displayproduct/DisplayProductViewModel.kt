package com.market.presentation.mainscreen.user.displayproduct

import androidx.lifecycle.*
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.productdetails.ProductDetails
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayProductViewModel @Inject constructor(val repoImp: UserRepoImp,
private val savedStateHandle: SavedStateHandle) :ViewModel(){


    val results: MutableLiveData<ResultState<ProductDetails>> = MutableLiveData()



    fun getProductDetails(productId: String, latitude: String,
                          longitude: String) {

        viewModelScope.launch {
            val response = repoImp.productDetails(productId,latitude,longitude)
            results.postValue(response)

        }


    }

    fun addComment(token:String, ProductId:String, rate: Float, comment:String, latitude: String,
                   longitude: String){
        viewModelScope.launch {
            val map :HashMap<String,String> = HashMap()
            map["product_id"]=ProductId
            map["rate"]=rate.toString()
            map["comment"]=comment
            val response = repoImp.addComments(map,token)

            when(response){
                is ResultState.Success<DefaultResponse> ->{

                }
                else ->{

                }
            }
            val responses = repoImp.productDetails(ProductId,latitude, longitude)
            results.postValue(responses)


        }


    }


    fun deleteComment(token:String, ProductId:String){
        viewModelScope.launch {

           repoImp.deleteComments(ProductId,token)




        }


    }

    fun editeComment(token:String, ProductId:String, rate: Float, comment:String, commentId: Int
        , latitude: String,
                     longitude: String){
        viewModelScope.launch {

            viewModelScope.launch {
                val map :HashMap<String,String> = HashMap()
                map["product_id"]=ProductId
                map["rate"]=rate.toString()
                map["comment"]=comment

                val response = repoImp.editeComments(map,token,commentId)

                when(response){
                    is ResultState.Success<DefaultResponse> ->{

                    }
                    else ->{

                    }
                }
                val responses = repoImp.productDetails(ProductId,latitude,longitude)
                results.postValue(responses)


            }



        }


    }

    
}