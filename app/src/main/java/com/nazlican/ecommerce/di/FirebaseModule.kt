package com.nazlican.ecommerce.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun bindAuthRepository() : FirebaseAuth = FirebaseAuth.getInstance()
    @Singleton
    @Provides
    fun bindStoreRepository() : FirebaseFirestore = FirebaseFirestore.getInstance()
}
