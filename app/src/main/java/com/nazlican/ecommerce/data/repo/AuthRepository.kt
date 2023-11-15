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
    fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun loginToFirebase(email: String, password: String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            try {

                val authSignIn = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (authSignIn.user != null) {
                    Resource.Success(true)
                } else
                    Resource.Error("An error occurred")
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun registerToFirebase(email: String, password: String, name: String, surname:String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val authSignUp =
                    firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (authSignUp.user != null) {

                    val uid = FirebaseAuth.getInstance().currentUser!!.uid
                    val user = User(
                        userId = uid,
                        email = email,
                        name = name,
                        surname = surname
                    )
                    firebaseFirestore.collection("users").document(uid).set(user).await()

                    Resource.Success(true)
                } else
                    Resource.Error("An error occured")
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getUser(): User {
        val user = firebaseFirestore.collection("users").document(getUserId()).get().await()

        return User(
            name = user.getString("name") ?: "",
            surname = user.getString("surname") ?: "",
            email = user.getString("email") ?: ""
        )
    }
}

