package com.nazlican.ecommerce.ui.productDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.AddToCart
import com.nazlican.ecommerce.data.model.BaseResponse
import com.nazlican.ecommerce.data.model.DetailResponse
import com.nazlican.ecommerce.data.repo.CartRepository
import com.nazlican.ecommerce.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val homeRepository: ProductRepository, private val cartRepository: CartRepository) : ViewModel(){

    private var _detailState = MutableLiveData<DetailState>()
    val detailState : MutableLiveData<DetailState> get() = _detailState

    fun getDetailProduct(id:Int) = viewModelScope.launch{
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = homeRepository.getDetailProducts(id)) {
            is Resource.Success -> DetailState.SuccessState(result.data)
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowPopUp(result.errorMessage)
        }
    }
    fun AddToCartProduct(addToCart: AddToCart) = viewModelScope.launch{
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = cartRepository.addToCart(addToCart)) {
            is Resource.Success -> DetailState.SuccessAddToCartState(result.data)
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowPopUp(result.errorMessage)
        }
    }
}
sealed interface DetailState {
    object Loading: DetailState
    data class SuccessState(val detailResponse: DetailResponse) :DetailState
    data class SuccessAddToCartState(val baseResponse: BaseResponse) :DetailState
    data class EmptyScreen(val failMessage:String):DetailState
    data class ShowPopUp(val errorMessage:String):DetailState
}
