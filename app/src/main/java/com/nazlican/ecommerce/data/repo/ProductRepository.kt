package com.nazlican.ecommerce.data.repo

import androidx.lifecycle.MutableLiveData
import com.nazlican.ecommerce.data.model.DetailResponse
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProductRepository(private val productService: ProductService) {

    private var job: Job? = null
    var productsLiveData = MutableLiveData<List<Product>?>()
    var saleProductsLiveData = MutableLiveData<List<Product>?>()
    var detailProductLiveData = MutableLiveData<DetailResponse?>()
    var categoryLiveData = MutableLiveData<List<Product?>>()

    fun getProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = productService.getMainProducts()
            if (result.isSuccessful) {
                result.body()?.let { productList ->
                    productsLiveData.postValue(productList.products.orEmpty())
                }
            } else {
                productsLiveData.postValue(null)
            }
        }
    }

    fun getSaleProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = productService.getSaleProducts()
            if (result.isSuccessful) {
                val products = result.body()?.products
                if (products != null) {
                    val saleProducts = products?.filter { it.saleState == true }
                    saleProductsLiveData.postValue(saleProducts)
                } else {
                    saleProductsLiveData.postValue(null)
                }
            }
        }
    }

    fun getDetailProducts(id: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = productService.detailProduct(id)
            if (result.isSuccessful) {
                result.body()?.let { detailProduct ->
                    detailProductLiveData.postValue(detailProduct.product)
                }
            } else {
                detailProductLiveData.postValue(null)
            }
        }
    }

    fun getProductsByCategory(category: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = productService.getProductsByCategory(category)
            if (result.isSuccessful) {
                result.body()?.let { byCategory ->
                    categoryLiveData.postValue(byCategory.products.orEmpty())
                }
            } else {
                detailProductLiveData.postValue(null)
            }
        }
    }
}