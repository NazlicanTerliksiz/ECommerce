package com.nazlican.ecommerce.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: ProductRepository):ViewModel() {

    private var _productsLiveData = MutableLiveData<List<Product>?>()
    val productsLiveData: LiveData<List<Product>?> get() = _productsLiveData

    private var _saleProductsLiveData = MutableLiveData<List<Product>?>()
    val saleProductsLiveData: LiveData<List<Product>?> get() = _saleProductsLiveData

    private var _categoryLiveData = MutableLiveData<List<Product>?>()
    val categoryLiveData : LiveData<List<Product>?> get() = _categoryLiveData

    init {
        _productsLiveData = homeRepository.productsLiveData
        _saleProductsLiveData = homeRepository.saleProductsLiveData
    }
    fun getProducts(){
        homeRepository.getProducts()
    }
    fun getSaleProducts(){
        homeRepository.getSaleProducts()
    }
    fun getProductsByCategory(category:String){
        homeRepository.getProductsByCategory(category)
    }

}