package com.market.presentation.mainscreen.user.search

import android.util.Log
import androidx.lifecycle.*
import com.market.data.models.get.search.SearchResults
import com.market.data.repo.UserRepoImp
import com.market.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repoImp: UserRepoImp,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val results: MutableLiveData<ResultState<SearchResults>> = MutableLiveData()


    fun getSearchText(): LiveData<String> {

        return savedStateHandle.getLiveData("String")
    }

    fun search(string: String) {
        savedStateHandle.getLiveData<String>("String").also {
            if (!string.equals(it.value.toString())) {
                savedStateHandle.set("String", string)
                performSearch(string)
            }
        }
    }

    private fun performSearch(string: String) {

        viewModelScope.launch {
            val response = repoImp.search(string)
            results.postValue(response)

        }


    }

}