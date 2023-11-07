package com.nazlican.ecommerce.data.repo

import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.mapper.mapToProductListUI
import com.nazlican.ecommerce.data.model.response.DetailResponse
import com.nazlican.ecommerce.data.model.response.ProductListUI
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productService: ProductService) {
    suspend fun getProducts(): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getMainProducts().body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapToProductListUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getSaleProducts(): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getSaleProducts().body()
                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapToProductListUI())
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

    suspend fun getProductsByCategory(category: String): Resource<List<ProductListUI>> {
        return try {
            val response = productService.getProductsByCategory(category).body()
            if (response?.status == 200) {
                Resource.Success(response.products.orEmpty().mapToProductListUI())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }
}
