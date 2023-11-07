package com.nazlican.ecommerce.data.mapper

import com.nazlican.ecommerce.data.model.response.Product
import com.nazlican.ecommerce.data.model.response.ProductListUI
import com.nazlican.ecommerce.data.model.response.ProductUI

fun Product.mapToProductUI() =
        ProductUI(
            category = category.orEmpty(),
            count = count ?: 0,
            description =description.orEmpty(),
            id = id ?:1,
            imageOne = imageOne.orEmpty(),
            imageThree = imageThree.orEmpty(),
            imageTwo = imageTwo.orEmpty(),
            price = price ?: 0.0,
            rate = rate ?: 0.0,
            salePrice = salePrice ?: 0.0,
            saleState = saleState ?: false,
            title = title.orEmpty(),
        )

fun List<Product>.mapToProductListUI() =
    this.map {
        ProductListUI(
            id = it.id ?:1,
            imageOne = it.imageOne.orEmpty(),
            price = it.price ?: 0.0,
            salePrice = it.salePrice ?: 0.0,
            title = it.title.orEmpty(),
            rate = it.rate ?: 0.0
        )
    }