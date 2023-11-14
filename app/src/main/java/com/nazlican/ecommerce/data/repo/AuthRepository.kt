package com.nazlican.ecommerce.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.response.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
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
                val authSignUp =
                    firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (authSignUp.user != null) {
                    Resource.Success(true)
                } else
                    Resource.Success(false)
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

   /* suspend fun addUserInfo(email: String): Resource<Boolean> = withContext(Dispatchers.IO) {
        try {
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val user = User(
                userId = uid,
                email = email
            )
            firebaseFirestore.collection("users").document(uid).set(user).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }*/

    fun addUserInfo(email: String,onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val user = User(
            userId = uid,
            email = email
        )
        firebaseFirestore.collection("users").document("${uid}").set(user).addOnSuccessListener {
            onSuccess.invoke()
        }.addOnFailureListener {
            onFailure.invoke(it.localizedMessage.orEmpty())
        }
    }

}

