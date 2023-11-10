package com.nazlican.ecommerce.data.repo

import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.mapper.mapProductEntityToProductUI
import com.nazlican.ecommerce.data.mapper.mapToProductEntity
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.data.source.local.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val productDao: ProductDao) {

    suspend fun addToFavorites(productListUI : ProductUI){
        productDao.addProduct(productListUI.mapToProductEntity())
    }

    suspend fun deleteFromFavorites(productUI : ProductUI){
        productDao.deleteProduct(productUI.mapToProductEntity())
    }

    suspend fun getFavorites(): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val products = productDao.getProducts()

                if (products.isEmpty()) {
                    Resource.Fail("Products not found")
                }else {
                    Resource.Success(products.mapProductEntityToProductUI())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

}
