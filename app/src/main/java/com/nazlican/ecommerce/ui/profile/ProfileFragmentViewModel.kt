package com.nazlican.ecommerce.ui.profile

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
class ProfileFragmentViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _storeState: MutableLiveData<StoreState> = MutableLiveData()
    val storeState: LiveData<StoreState> get() = _storeState

    fun addUserInfo(email: String) {
        viewModelScope.launch {
            _storeState.value = StoreState.Loading

            _storeState.value = when (val result = authRepository.addUserInfo(email)) {
                is Resource.Success -> StoreState.AddUserInfoSuccessState
                is Resource.Error -> StoreState.ErrorState(result.errorMessage)
                is Resource.Fail -> StoreState.FailState(result.failMessage)
            }
        }
    }

}
sealed interface StoreState {
    object Loading : StoreState
    object AddUserInfoSuccessState : StoreState
    data class FailState(val failMessage: String) : StoreState
    data class ErrorState(val errorMessage: String) : StoreState
}
