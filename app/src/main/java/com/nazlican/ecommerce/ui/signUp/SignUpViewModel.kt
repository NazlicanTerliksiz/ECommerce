package com.nazlican.ecommerce.ui.signUp

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nazlican.ecommerce.data.model.User

class SignUpViewModel:ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    fun initializeFirebase() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }
    fun signUpToFirebase(email: String, password: String,onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            onSuccess.invoke()
            addUser(email, onSuccess, onFailure)
        }.addOnFailureListener {
            onFailure.invoke(it.localizedMessage.orEmpty())
        }
    }
    fun addUser(email: String,onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val user = User(
            userId = uid,
            email = email
        )
        db.collection("users").document("${uid}").set(user).addOnSuccessListener {
            onSuccess.invoke()
        }.addOnFailureListener {
            onFailure.invoke(it.localizedMessage.orEmpty())
        }
    }

}