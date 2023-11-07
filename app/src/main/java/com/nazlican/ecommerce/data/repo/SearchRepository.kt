package com.nazlican.ecommerce.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nazlican.ecommerce.data.model.response.Product
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchRepository(private val productService: ProductService) {

    private var job: Job? = null
    val searchProductLiveData = MutableLiveData<List<Product>?>()

    fun searchFromProduct(query:String)  {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result =productService.searchFromProduct(query)
            if (result.isSuccessful) {
                result.body()?.let {products ->
                    Log.d("if içi", products.toString())
                    searchProductLiveData.postValue(products.products)
                }
            }else{
                searchProductLiveData.postValue(null)
            }
        }
    }
}