package com.nazlican.ecommerce.di

import com.nazlican.ecommerce.data.repo.HomeRepository
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
    fun provideProductsRepository(productService: ProductService) = HomeRepository(productService)
}