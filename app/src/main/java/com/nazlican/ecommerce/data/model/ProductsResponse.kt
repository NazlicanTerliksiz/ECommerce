package com.nazlican.ecommerce.data.model

data class ProductsResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)
