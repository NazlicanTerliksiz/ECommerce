package com.nazlican.ecommerce.ui.signUp

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private val _registerState: MutableLiveData<RegisterState> = MutableLiveData()
    val registerState: LiveData<RegisterState> get() = _registerState

    fun registerToFirebase(email: String, password: String, name:String, surname:String) {
        viewModelScope.launch {
            if (checkFields(email, password)) {
                _registerState.value = RegisterState.Loading

                _registerState.value =
                    when (val result = authRepository.registerToFirebase(email, password, name, surname )) {
                        is Resource.Success -> RegisterState.RegisterSuccessState
                        is Resource.Fail -> RegisterState.ShowPopUp(result.failMessage)
                        is Resource.Error -> RegisterState.ShowPopUp(result.errorMessage)
                    }
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                _registerState.value = RegisterState.ShowPopUp("mail can't be empty")
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _registerState.value = RegisterState.ShowPopUp("valid email required")
                false
            }

            password.isEmpty() -> {
                _registerState.value = RegisterState.ShowPopUp("password can't be empty")
                false
            }

            password.length < 6 -> {
                _registerState.value = RegisterState.ShowPopUp("6 char password required")
                false
            }

            else -> true
        }
    }

}

sealed interface RegisterState {
    object Loading : RegisterState
    object RegisterSuccessState : RegisterState
    object AddUserInfoSuccessState : RegisterState
    data class ShowPopUp(val errorMessage: String) : RegisterState
}