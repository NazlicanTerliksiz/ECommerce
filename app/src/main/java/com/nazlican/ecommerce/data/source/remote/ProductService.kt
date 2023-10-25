package com.nazlican.ecommerce.data.source.remote

import com.nazlican.ecommerce.data.model.AddToCart
import com.nazlican.ecommerce.data.model.DeleteFromCart
import com.nazlican.ecommerce.data.model.Detail
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("get_products_by_category.php")
    suspend fun getProductsByCategory(
        @Query("category") category: String
    ) : Response<ProductsResponse>

    @GET("get_cart_products.php?userId=b3sa6dj721312ssadas21d")
    suspend fun getCartProduct(): Response<ProductsResponse>

    @POST("add_to_cart.php")
    suspend fun addToCart(
        @Body addToCart: AddToCart
    ): Response<AddToCart>

    @POST("delete_from_cart.php")
    suspend fun deleteFromCart(
        @Body deleteFromCart: DeleteFromCart
    ): Response<DeleteFromCart>

    @GET("search_product.php")
    suspend fun searchFromProduct(
        @Query("query") query: String
    ): Response<Product>
}