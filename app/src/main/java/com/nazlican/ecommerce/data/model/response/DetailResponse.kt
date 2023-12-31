package com.nazlican.ecommerce.data.model.response

data class DetailResponse(
    val category: String?,
    val userId : String?,
    val count: Int?,
    val description: String?,
    val id: Int?,
    val imageOne: String,
    val imageThree: String?,
    val imageTwo: String?,
    val price: Double?,
    val rate: Double?,
    val salePrice: Double?,
    val saleState: Boolean?,
    val title: String?
)
