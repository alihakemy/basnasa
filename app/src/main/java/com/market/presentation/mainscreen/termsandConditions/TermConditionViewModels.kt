package com.market.presentation.mainscreen.termsandConditions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.terms.TermsCondtionModel
import com.market.data.repo.AuthenticationRepository
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermConditionViewModels @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val repo: UserRepoImp
) : ViewModel() {

    val results: MutableLiveData<ResultState<TermsCondtionModel>> = MutableLiveData()

    fun getPages(type: String) {

        viewModelScope.launch {
            results.postValue(repo.getTerms(type))
        }

    }


}