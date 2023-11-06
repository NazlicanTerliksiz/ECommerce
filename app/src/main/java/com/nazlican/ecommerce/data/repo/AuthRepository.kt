package com.nazlican.ecommerce.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor (private val firebaseAuth: FirebaseAuth) {
    suspend fun loginToFirebase(email: String, password: String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val authSignIn = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (authSignIn.user != null) {
                    Resource.Success(true)
                } else
                    Resource.Success(false)
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun registerToFirebase(email: String, password: String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val authSignUp = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (authSignUp.user != null) {
                    Resource.Success(true)
                } else
                    Resource.Success(false)
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }
}

