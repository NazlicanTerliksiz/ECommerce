package com.nazlican.ecommerce.di

import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.data.repo.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuth(firebaseAuth: FirebaseAuth) = AuthRepository(firebaseAuth)
}