package com.nazlican.ecommerce.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.repo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: HomeRepository):ViewModel() {

    private var _productsLiveData = MutableLiveData<List<Product>?>()
    val productsLiveData: LiveData<List<Product>?> get() = _productsLiveData

    init {
        _productsLiveData = productRepository.productsLiveData
    }

    fun getProducts(){
        productRepository.getProducts()
    }

}