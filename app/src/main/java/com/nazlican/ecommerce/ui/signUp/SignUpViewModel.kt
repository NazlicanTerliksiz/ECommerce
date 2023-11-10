package com.nazlican.ecommerce.ui.signUp

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
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _registerState: MutableLiveData<RegisterState> = MutableLiveData()
    val registerState: LiveData<RegisterState> get() = _registerState

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

}
sealed interface RegisterState {
    object Loading : RegisterState
    object RegisterSuccessState : RegisterState
    object AddUserInfoSuccessState : RegisterState
    data class RegisterFailState(val failMessage: String) : RegisterState
    data class RegisterErrorState(val errorMessage: String) : RegisterState
}