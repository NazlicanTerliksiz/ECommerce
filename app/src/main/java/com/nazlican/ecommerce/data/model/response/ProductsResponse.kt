package com.nazlican.ecommerce.data.model.response

data class ProductsResponse(
    val products: List<Product>?,
) : BaseResponse()
