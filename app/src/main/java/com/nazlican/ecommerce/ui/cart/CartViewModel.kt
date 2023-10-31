package com.nazlican.ecommerce.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazlican.ecommerce.data.model.DeleteFromCart
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository):ViewModel() {

    private var _cartProductsLiveData = MutableLiveData<List<Product>?>()
    val cartProductsLiveData : LiveData<List<Product>?> get() = _cartProductsLiveData

    private var _deleteProductsLiveData = MutableLiveData<DeleteFromCart?>()
    val deleteProductsLiveData : LiveData<DeleteFromCart?> get() = _deleteProductsLiveData

    init {
        _cartProductsLiveData = cartRepository.cartProductsLiveData
        _deleteProductsLiveData = cartRepository.deleteProductsLiveData
    }

    fun getCartProduct(userId: String){
        cartRepository.cartProducts(userId)
    }

    fun deleteFromCart(deleteFromCart: DeleteFromCart){
        cartRepository.deleteFromCart(deleteFromCart)
    }
}