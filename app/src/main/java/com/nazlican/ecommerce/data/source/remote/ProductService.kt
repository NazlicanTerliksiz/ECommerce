package com.nazlican.ecommerce.data.source.remote

import com.nazlican.ecommerce.data.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface ProductService {

    @GET("get_products.php")
    suspend fun getMainProducts(): Response<ProductsResponse>

    @GET("get_product_detail.php")
    suspend fun getProductDetail(
        @Query("id") id:Int
    )
}