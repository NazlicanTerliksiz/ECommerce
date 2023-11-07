package com.nazlican.ecommerce.data.source.remote

import com.nazlican.ecommerce.data.model.request.AddToCart
import com.nazlican.ecommerce.data.model.response.BaseResponse
import com.nazlican.ecommerce.data.model.response.Categories
import com.nazlican.ecommerce.data.model.request.DeleteFromCart
import com.nazlican.ecommerce.data.model.response.Detail
import com.nazlican.ecommerce.data.model.response.ProductsResponse
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

    @GET("get_categories.php")
    suspend fun getCategoryName(): Response<Categories>

    @GET("get_cart_products.php")
    suspend fun getCartProducts(
        @Query("userId") userId:String
    ): Response<ProductsResponse>

    @POST("add_to_cart.php")
    suspend fun addToCart(
        @Body addToCart: AddToCart
    ): Response<BaseResponse>

    @POST("delete_from_cart.php")
    suspend fun deleteFromCart(
        @Body deleteFromCart: DeleteFromCart
    ): Response<BaseResponse>

    @GET("search_product.php")
    suspend fun searchFromProduct(
        @Query("query") query: String
    ): Response<ProductsResponse>
}