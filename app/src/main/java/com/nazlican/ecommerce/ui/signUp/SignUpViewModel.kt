package com.nazlican.ecommerce.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.response.User
import com.nazlican.ecommerce.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _registerState: MutableLiveData<RegisterState> = MutableLiveData()
    val registerState: LiveData<RegisterState> get() = _registerState

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    fun initializeFirebase() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    fun registerToFirebase(email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading

            _registerState.value = when (val result = authRepository.registerToFirebase(email, password)) {
                is Resource.Success -> RegisterState.RegisterSuccessState
                is Resource.Fail -> RegisterState.RegisterFailState(result.failMessage)
                is Resource.Error -> RegisterState.RegisterErrorState(result.errorMessage)
            }
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
sealed interface RegisterState {
    object Loading : RegisterState
    object RegisterSuccessState : RegisterState
    data class RegisterFailState(val failMessage: String) : RegisterState
    data class RegisterErrorState(val errorMessage: String) : RegisterState
}