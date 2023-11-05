package com.nazlican.ecommerce.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    private var _categoryLiveData = MutableLiveData<List<Product>?>()
    val categoryLiveData: LiveData<List<Product>?> get() = _categoryLiveData

    private var _categoryNameLiveData = MutableLiveData<List<String>?>()
    val categoryNameLiveData: LiveData<List<String>?> get() = _categoryNameLiveData

    init {
        //_productsLiveData = productRepository.productsLiveData
        //_saleProductsLiveData = productRepository.saleProductsLiveData
        _categoryLiveData = productRepository.categoryLiveData
        _categoryNameLiveData = productRepository.categoryNameLiveData
    }

    fun getProducts() = viewModelScope.launch {
        _homeState.value = HomeState.Loading

        _homeState.value = when(val result = productRepository.getProducts()) {
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
    fun getProductsByCategory(category: String) {
        //if (category == "All") return getProducts()
        productRepository.getProductsByCategory(category)
    }

    fun getCategoryName() {
        productRepository.getCategoryName()
    }
}

sealed interface HomeState {
    object Loading : HomeState
    data class SuccessProductState(val products: List<Product>) : HomeState
    data class SuccessSaleProductState(val products: List<Product>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowPopUp(val errorMessage: String) : HomeState
}