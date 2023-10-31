package com.nazlican.ecommerce.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(private val searchRepository: SearchRepository):ViewModel() {

    private var _searchProductLiveData = MutableLiveData<List<Product>?>()
    val searchProductLiveData: LiveData<List<Product>?> get() = _searchProductLiveData

    init {
        _searchProductLiveData = searchRepository.searchProductLiveData
    }

    fun searchProduct(query:String){
        searchRepository.searchFromProduct(query)
    }

}