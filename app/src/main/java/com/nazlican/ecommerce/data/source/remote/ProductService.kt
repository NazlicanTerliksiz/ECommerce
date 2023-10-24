package com.nazlican.ecommerce.data.source.remote

import com.nazlican.ecommerce.data.model.Detail
import com.nazlican.ecommerce.data.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface ProductService {

    @GET("get_products.php")
    suspend fun getMainProducts(): Response<ProductsResponse>

    @GET("get_product_detail.php")
    suspend fun detailProduct(
        @Query("id") id: Int
    ): Response<Detail>

    @GET("get_sale_products.php")
    suspend fun getSaleProducts() : Response<ProductsResponse>
}