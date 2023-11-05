package com.nazlican.ecommerce.data.repo

import androidx.lifecycle.MutableLiveData
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.DetailResponse
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductRepository(private val productService: ProductService) {

    private var job: Job? = null
    var categoryLiveData = MutableLiveData<List<Product>?>()
    var categoryNameLiveData = MutableLiveData<List<String>?>()

    suspend fun getProducts(): Resource<List<Product>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getMainProducts().body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getSaleProducts(): Resource<List<Product>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getSaleProducts().body()
                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }


    suspend fun getDetailProducts(id: Int): Resource<DetailResponse> {
        return try {
            val response = productService.detailProduct(id).body()
            if (response?.status == 200 && response.product != null) {
                Resource.Success(response.product)
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    fun getCategoryName() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = productService.getCategoryName()
            if (result.isSuccessful) {
                result.body()?.let { categoryName ->
                    categoryNameLiveData.postValue(categoryName.categories)
                }
            } else {
                categoryNameLiveData.postValue(null)
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
                categoryLiveData.postValue(null)
            }
        }
    }
}