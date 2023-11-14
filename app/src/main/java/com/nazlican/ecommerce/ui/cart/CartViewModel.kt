package com.nazlican.ecommerce.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.request.ClearCart
import com.nazlican.ecommerce.data.model.response.BaseResponse
import com.nazlican.ecommerce.data.model.request.DeleteFromCart
import com.nazlican.ecommerce.data.model.response.ProductUI
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

    fun deleteFromCart(deleteFromCart: DeleteFromCart) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value =
            when (val result = cartRepository.deleteFromCart(deleteFromCart)) {
                is Resource.Success -> CartState.DeleteProductSuccessState(result.data)
                is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
                is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
            }
    }

    fun clearCart(clearCart: ClearCart) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value =
            when (val result = cartRepository.clearCart(clearCart)) {
                is Resource.Success -> CartState.ClearCartSuccessState(result.data)
                is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
                is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
            }
    }

    fun totalPrice(products: List<ProductUI>): Double {
        var totalPrice = 0.0
        for (i in products.indices) {
            if (products[i].saleState) {
                totalPrice += products[i].salePrice
            } else
                totalPrice += products[i].price
        }
        return totalPrice
    }
}

sealed interface CartState {
    object Loading : CartState
    data class CartProductSuccessState(val products: List<ProductUI>) : CartState
    data class DeleteProductSuccessState(val baseResponse: BaseResponse) : CartState
    data class ClearCartSuccessState(val baseResponse: BaseResponse) : CartState
    data class EmptyScreen(val failMessage: String) : CartState
    data class ShowPopUp(val errorMessage: String) : CartState
}