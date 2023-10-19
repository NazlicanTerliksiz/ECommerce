package com.nazlican.ecommerce.data.repo

import androidx.lifecycle.MutableLiveData
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeRepository(private val productService: ProductService) {

    private var job: Job? = null
    var productsLiveData = MutableLiveData<List<Product>?>()

    fun getProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = productService.getMainProducts()
            if (result.isSuccessful) {
                result.body()?.let {productList ->
                    println("deneme $productList")
                    productsLiveData.postValue(productList.products.orEmpty())
                }
            }else{
                productsLiveData.postValue(null)
            }
        }
    }
}