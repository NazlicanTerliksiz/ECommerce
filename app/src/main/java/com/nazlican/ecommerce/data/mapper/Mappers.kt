package com.nazlican.ecommerce.data.mapper

import com.nazlican.ecommerce.data.model.response.DetailResponse
import com.nazlican.ecommerce.data.model.response.Product
import com.nazlican.ecommerce.data.model.response.ProductEntity
import com.nazlican.ecommerce.data.model.response.ProductUI

fun DetailResponse.mapToProductUI(favorites: List<Int>) =
        ProductUI(
            category = category.orEmpty(),
            userId = userId.orEmpty(),
            count = count ?: 0,
            description =description.orEmpty(),
            id = id ?:1,
            imageOne = imageOne,
            imageThree = imageThree.orEmpty(),
            imageTwo = imageTwo.orEmpty(),
            price = price ?: 0.0,
            rate = rate ?: 0.0,
            salePrice = salePrice ?: 0.0,
            saleState = saleState ?: false,
            title = title.orEmpty(),
            isFav = favorites.contains(id)
        )

fun List<Product>.mapProductToProductUI(favorites: List<Int>) =
    this.map {
        ProductUI(
            id = it.id,
            userId = it.userId.orEmpty(),
            imageOne = it.imageOne.orEmpty(),
            imageTwo = it.imageTwo.orEmpty(),
            imageThree = it.imageThree.orEmpty(),
            price = it.price ?: 0.0,
            salePrice = it.salePrice ?: 0.0,
            title = it.title.orEmpty(),
            rate = it.rate ?: 0.0,
            saleState = it.saleState ?: false,
            description = it.description.orEmpty(),
            count = it.count ?: 0,
            category = it.category.orEmpty(),
            isFav = favorites.contains(it.id)
        )
    }

fun ProductUI.mapToProductEntity(userId:String) =
    ProductEntity(
        productId = id,
        userId = userId,
        title = title,
        price = price,
        imageOne = imageOne,
        imageTwo = imageTwo,
        imageThree = imageThree,
        salePrice = salePrice,
        saleState = saleState,
        description = description,
        count = count,
        category = category,
        rate = rate,
        isfav = isFav
    )

fun List<ProductEntity>.mapProductEntityToProductUI() =
    map {
        ProductUI(
            id = it.productId ?: 1,
            userId = it.userId.orEmpty(),
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            imageOne = it.imageOne.orEmpty(),
            imageTwo = it.imageTwo.orEmpty(),
            imageThree = it.imageThree.orEmpty(),
            salePrice = it.salePrice ?: 0.0,
            saleState = it.saleState ?: false,
            description = it.description.orEmpty(),
            count = it.count ?: 0,
            category = it.category.orEmpty(),
            rate = it.rate ?: 0.0,
            isFav = it.isfav ?: false
        )
    }