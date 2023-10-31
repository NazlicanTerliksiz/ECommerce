package com.nazlican.ecommerce.di

import com.nazlican.ecommerce.data.repo.CartRepository
import com.nazlican.ecommerce.data.repo.ProductRepository
import com.nazlican.ecommerce.data.repo.SearchRepository
import com.nazlican.ecommerce.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RepositoryModule {
    @Provides
    @Singleton
    fun provideProductsRepository(productService: ProductService) = ProductRepository(productService)

    @Provides
    @Singleton
    fun provideCartRepository(productService: ProductService) = CartRepository(productService)

    @Provides
    @Singleton
    fun provideSearchRepository(productService: ProductService) = SearchRepository(productService)

}