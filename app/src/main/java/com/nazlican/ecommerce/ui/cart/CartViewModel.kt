package com.nazlican.ecommerce.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.BaseResponse
import com.nazlican.ecommerce.data.model.DeleteFromCart
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: MutableLiveData<CartState> get() = _cartState

    fun getCartProduct(userId: String) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = cartRepository.cartProducts(userId)) {
            is Resource.Success -> CartState.CartProductSuccessState(result.data)
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }

    }
    fun deleteFromCart(deleteFromCart: DeleteFromCart, userId: String) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = cartRepository.deleteFromCart(deleteFromCart, userId)) {
            is Resource.Success -> CartState.DeleteProductSuccessState(result.data)
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }
    }
}

sealed interface CartState {
    object Loading : CartState
    data class CartProductSuccessState(val products: List<Product>) : CartState
    data class DeleteProductSuccessState(val baseResponse: BaseResponse) : CartState
    data class EmptyScreen(val failMessage: String) : CartState
    data class ShowPopUp(val errorMessage: String) : CartState
}