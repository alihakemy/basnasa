package com.market.presentation.mainscreen.trader.addproduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.User
import com.market.data.models.get.addComment.DefaultResponse
import com.market.data.models.get.categories.Categories
import com.market.data.repo.AuthenticationRepository
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val repo: UserRepoImp
) : ViewModel() {

    val categories: MutableLiveData<ResultState<Categories>> =
        MutableLiveData<ResultState<Categories>>()
            .also {
                getCategories()
            }

    val results: MutableLiveData<ResultState<DefaultResponse>> =
        MutableLiveData<ResultState<DefaultResponse>>()

    private fun getCategories() {

        viewModelScope.launch(Dispatchers.IO) {

            categories.postValue(authenticationRepository.getCategories())

        }
    }

    fun addProduct(
        list: ArrayList<MultipartBody.Part>, image: MultipartBody.Part,
        category_id: String,
        mainprice: String,
        discount: String,
        stoke: String,
        name: String,
        currecny: String,
        about: String
    ) {
        viewModelScope.launch {
            results.postValue(
                repo.addProduct(
                    list,
                    image,
                    category_id = category_id,
                    mainprice = mainprice,
                    discount = discount,
                    stoke = stoke,
                    name = name,
                    currecny = currecny,
                    about = about
                )
            )
        }
    }


}