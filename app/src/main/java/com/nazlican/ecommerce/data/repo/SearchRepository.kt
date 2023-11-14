package com.nazlican.ecommerce.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.mapper.mapProductToProductUI
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.data.source.local.ProductDao
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    suspend fun searchFromProduct(query: String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val userId = FirebaseAuth.getInstance().currentUser!!.uid
                val favorites = productDao.getProductIds(userId)
                val response = productService.searchFromProduct(query).body()
                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }
}
