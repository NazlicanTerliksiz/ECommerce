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

    suspend fun addToFavorites(productListUI : ProductUI, userId:String){
        productDao.addProduct(productListUI.mapToProductEntity(userId))
    }

    suspend fun deleteFromFavorites(productUI : ProductUI, userId:String){
        productDao.deleteProduct(productUI.mapToProductEntity(userId))
    }

    suspend fun getFavorites(userId:String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val products = productDao.getProducts(userId)

                if (products.isEmpty()) {
                    Resource.Fail("There are no products in your favorites")
                }else {
                    Resource.Success(products.mapProductEntityToProductUI())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }
    suspend fun clearAllFavorites(userId:String){
        productDao.clearAllFavorites(userId)
    }

}
