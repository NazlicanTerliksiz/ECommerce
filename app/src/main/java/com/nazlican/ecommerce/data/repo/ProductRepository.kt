package com.nazlican.ecommerce.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.mapper.mapProductToProductUI
import com.nazlican.ecommerce.data.mapper.mapToProductUI
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.data.source.local.ProductDao
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    suspend fun getProducts(): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val favorites = productDao.getProductIds(userId)
                val response = productService.getMainProducts().body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getSaleProducts(): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val favorites = productDao.getProductIds(userId)
                val response = productService.getSaleProducts().body()
                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }


    suspend fun getDetailProducts(id: Int): Resource<ProductUI> {
        return try {
            val favorites = productDao.getProductIds(userId)
            val response = productService.detailProduct(id).body()
            if (response?.status == 200 && response.product != null) {
                Resource.Success(response.product.mapToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getCategoryName(): Resource<List<String>> {
        return try {
            val response = productService.getCategoryName().body()
            if (response?.status == 200) {
                Resource.Success(response.categories)
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getProductsByCategory(category: String): Resource<List<ProductUI>> {
        return try {
            val favorites = productDao.getProductIds(userId)
            val response = productService.getProductsByCategory(category).body()
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
