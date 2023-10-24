package com.nazlican.ecommerce.ui.productDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazlican.ecommerce.data.model.DetailResponse
import com.nazlican.ecommerce.data.repo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel(){

    private var _detailProductLiveData = MutableLiveData<DetailResponse?>()
    val detailProductLiveData : MutableLiveData<DetailResponse?> get() = _detailProductLiveData

    init {
        _detailProductLiveData = homeRepository.detailProductLiveData
    }
    fun getDetailProduct(id:Int){
        homeRepository.getDetailProducts(id)
    }


}