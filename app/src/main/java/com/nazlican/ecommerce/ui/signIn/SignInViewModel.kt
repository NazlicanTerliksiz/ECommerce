package com.nazlican.ecommerce.ui.signIn

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    fun loginToFirebase(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            onSuccess.invoke()
        }.addOnFailureListener {
            onFailure.invoke(it.localizedMessage.orEmpty())
        }
    }
}