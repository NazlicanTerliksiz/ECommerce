package com.nazlican.ecommerce.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.data.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {

    private var _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> get() = _searchState

    fun searchProduct(query: String) = viewModelScope.launch {
        _searchState.value = SearchState.Loading

        _searchState.value = when (val result = searchRepository.searchFromProduct(query)) {
            is Resource.Success -> SearchState.SuccessSearchState(result.data)
            is Resource.Fail -> SearchState.EmptyScreen(result.failMessage)
            is Resource.Error -> SearchState.ShowPopUp(result.errorMessage)
        }
    }
}

sealed interface SearchState {
    object Loading : SearchState
    data class SuccessSearchState(val products: List<ProductUI>) : SearchState
    data class EmptyScreen(val failMessage: String) : SearchState
    data class ShowPopUp(val errorMessage: String) : SearchState
}
