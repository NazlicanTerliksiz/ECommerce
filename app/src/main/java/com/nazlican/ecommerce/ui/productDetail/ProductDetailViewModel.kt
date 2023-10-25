package com.nazlican.ecommerce.ui.productDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazlican.ecommerce.data.model.AddToCart
import com.nazlican.ecommerce.data.model.DetailResponse
import com.nazlican.ecommerce.data.repo.CartRepository
import com.nazlican.ecommerce.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.checkerframework.checker.units.qual.A
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val homeRepository: ProductRepository, private val cartRepository: CartRepository) : ViewModel(){

    private var _detailProductLiveData = MutableLiveData<DetailResponse?>()
    val detailProductLiveData : MutableLiveData<DetailResponse?> get() = _detailProductLiveData

    private var _addToCartLiveData = MutableLiveData<AddToCart?>()
    val addToCartLiveData : MutableLiveData<AddToCart?> get() = _addToCartLiveData

    init {
        _detailProductLiveData = homeRepository.detailProductLiveData
        _addToCartLiveData = cartRepository.addToCartLiveData
    }
    fun getDetailProduct(id:Int){
        homeRepository.getDetailProducts(id)
    }

    fun AddToCartProduct(addToCart: AddToCart){
        cartRepository.addToCart(addToCart)
    }


}