package com.nazlican.ecommerce.ui.signIn

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
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private val _logInState: MutableLiveData<LogInState> = MutableLiveData()
    val logInState: LiveData<LogInState> get() = _logInState

    fun loginToFirebase(email: String, password: String) {
        viewModelScope.launch {
            if (checkFields(email, password)) {
                _logInState.value = LogInState.Loading

                _logInState.value =
                    when (val result = authRepository.loginToFirebase(email, password)) {
                        is Resource.Success -> LogInState.LoginSuccessState
                        is Resource.Fail -> LogInState.ShowPopUp(result.failMessage)
                        is Resource.Error -> LogInState.ShowPopUp(result.errorMessage)
                    }
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                _logInState.value = LogInState.ShowPopUp("mail can't be empty")
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _logInState.value = LogInState.ShowPopUp("valid email required")
                false
            }

            password.isEmpty() -> {
                _logInState.value = LogInState.ShowPopUp("password can't be empty")
                false
            }

            password.length < 6 -> {
                _logInState.value = LogInState.ShowPopUp("6 char password required")
                false
            }

            else -> true
        }
    }
}

sealed interface LogInState {
    object Loading : LogInState
    object LoginSuccessState : LogInState
    data class ShowPopUp(val errorMessage: String) : LogInState
}