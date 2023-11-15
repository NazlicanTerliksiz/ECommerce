package com.nazlican.ecommerce.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.data.model.response.User
import com.nazlican.ecommerce.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {


    private var _profileState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState> get() = _profileState

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() = viewModelScope.launch {
        _profileState.value = ProfileState.SuccessState(authRepository.getUser())

    }
    fun logOut() = viewModelScope.launch {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
    }
}

sealed interface ProfileState {
    data class SuccessState(val user: User) : ProfileState
}