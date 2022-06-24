package com.market.presentation.mainscreen.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.market.data.models.get.categories.Categories
import com.market.data.models.get.notification.NotificationModel
import com.market.data.repo.AuthenticationRepository
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val repo: UserRepoImp
) : ViewModel() {

    val categories: MutableLiveData<ResultState<NotificationModel>> =
        MutableLiveData<ResultState<NotificationModel>>()
            .also {
                getCategories()
            }

    private fun getCategories() {

        viewModelScope.launch(Dispatchers.IO) {

            categories.postValue(authenticationRepository.getNotification())

        }
    }
}