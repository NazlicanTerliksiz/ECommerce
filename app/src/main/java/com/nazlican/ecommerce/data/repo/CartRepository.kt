package com.nazlican.ecommerce.data.repo

import com.nazlican.ecommerce.data.model.request.AddToCart
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.mapper.mapToProductListUI
import com.nazlican.ecommerce.data.model.response.BaseResponse
import com.nazlican.ecommerce.data.model.request.DeleteFromCart
import com.nazlican.ecommerce.data.model.response.ProductListUI
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository(private val productService: ProductService) {

    suspend fun cartProducts(userId: String): Resource<List<ProductListUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getCartProducts(userId).body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapToProductListUI())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun addToCart(addToCart: AddToCart): Resource<BaseResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.addToCart(addToCart).body()

                if (response?.status == 200) {
                    Resource.Success(response)
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun deleteFromCart(deleteFromCart: DeleteFromCart): Resource<BaseResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.deleteFromCart(deleteFromCart).body()

                if (response?.status == 200) {
                    Resource.Success(response)
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

}