package com.nazlican.ecommerce.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nazlican.ecommerce.data.repo.AuthRepository
import com.nazlican.ecommerce.data.repo.CartRepository
import com.nazlican.ecommerce.data.repo.ProductRepository
import com.nazlican.ecommerce.data.repo.SearchRepository
import com.nazlican.ecommerce.data.source.local.ProductDao
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
    fun provideProductsRepository(productService: ProductService, productDao: ProductDao) = ProductRepository(productService, productDao)

    @Provides
    @Singleton
    fun provideCartRepository(productService: ProductService, productDao: ProductDao) = CartRepository(productService, productDao)

    @Provides
    @Singleton
    fun provideSearchRepository(productService: ProductService, productDao: ProductDao) = SearchRepository(productService, productDao)

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore) =
        AuthRepository(firebaseAuth, firebaseFirestore)

}