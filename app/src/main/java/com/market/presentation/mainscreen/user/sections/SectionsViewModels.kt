package com.market.presentation.mainscreen.user.sections

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.setions.Sections
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionsViewModels @Inject constructor(
    private val repoImp: UserRepoImp
) : ViewModel() {



    val results :MutableLiveData<ResultState<Sections>> =MutableLiveData()


    fun getSectionCategories(
        categoriesId: String,
        token: String,
        latitude: String,
        longitude: String
    ) {
        viewModelScope.launch {
            results.postValue(  repoImp.getSectionCategories(categoriesId, token, latitude, longitude))

        }
    }

    fun getSubSectionCategories(
        categoriesId: String,
        subcategoriesId: String,
        token: String,
        latitude: String,
        longitude: String
    ) {
        viewModelScope.launch {
            results.postValue(  repoImp.getSectionSubCategories(categoriesId,subcategoriesId
                , token, latitude, longitude))

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