package com.nazlican.ecommerce.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.data.repo.FavoritesRepository
import com.nazlican.ecommerce.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val favoritesRepository: FavoritesRepository
) :
    ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    fun getProducts() = viewModelScope.launch {
        _homeState.value = HomeState.Loading

        _homeState.value = when (val result = productRepository.getProducts()) {
            is Resource.Success -> HomeState.SuccessProductState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    fun getSaleProducts() = viewModelScope.launch {
        _homeState.value = HomeState.Loading

        _homeState.value = when (val result = productRepository.getSaleProducts()) {
            is Resource.Success -> HomeState.SuccessSaleProductState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    fun getCategoryName() = viewModelScope.launch {
        _homeState.value = HomeState.Loading

        _homeState.value = when (val result = productRepository.getCategoryName()) {
            is Resource.Success -> HomeState.SuccessCategoryNameState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    fun getProductsByCategory(category: String) = viewModelScope.launch {
        _homeState.value = HomeState.Loading

        _homeState.value =
            when (val result = productRepository.getProductsByCategory(category)) {
                is Resource.Success -> HomeState.SuccessCategoryProductState(result.data)
                is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
                is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
            }
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        if (product.isFav) {
            favoritesRepository.deleteFromFavorites(product, userId)
        } else {
            favoritesRepository.addToFavorites(product, userId)
        }
        getProducts()
        getSaleProducts()
    }
}

sealed interface HomeState {
    object Loading : HomeState
    data class SuccessProductState(val products: List<ProductUI>) : HomeState
    data class SuccessSaleProductState(val products: List<ProductUI>) : HomeState
    data class SuccessCategoryNameState(val category: List<String>) : HomeState
    data class SuccessCategoryProductState(val products: List<ProductUI>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowPopUp(val errorMessage: String) : HomeState
}