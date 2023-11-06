package com.nazlican.ecommerce.ui.signIn

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
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _logInState: MutableLiveData<LogInState> = MutableLiveData()
    val logInState: LiveData<LogInState> get() = _logInState

    fun loginToFirebase(email: String, password: String) {
        viewModelScope.launch {
            _logInState.value = LogInState.Loading

            _logInState.value = when (val result = authRepository.loginToFirebase(email, password)) {
                is Resource.Success -> LogInState.LoginSuccessState
                is Resource.Fail -> LogInState.LoginFailState(result.failMessage)
                is Resource.Error -> LogInState.LoginErrorState(result.errorMessage)
            }
        }
    }
}
sealed interface LogInState {
    object Loading : LogInState
    object LoginSuccessState : LogInState
    data class LoginFailState(val failMessage: String) : LogInState
    data class LoginErrorState(val errorMessage: String) : LogInState
}