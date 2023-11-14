package com.nazlican.ecommerce.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.common.Resource
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.data.repo.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoritesRepository: FavoritesRepository) :
    ViewModel() {

    private var _favoriteState = MutableLiveData<FavoriteState>()
    val favoriteState: LiveData<FavoriteState> get() = _favoriteState

    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    fun getFavorites() = viewModelScope.launch {
        _favoriteState.value = FavoriteState.Loading

        _favoriteState.value = when (val result = favoritesRepository.getFavorites(userId)) {
            is Resource.Success -> FavoriteState.SuccessFavoriteState(result.data)
            is Resource.Fail -> FavoriteState.EmptyScreen(result.failMessage)
            is Resource.Error -> FavoriteState.ShowPopUp(result.errorMessage)
        }
    }

    fun deleteFromFavorites(products: ProductUI) = viewModelScope.launch {

        favoritesRepository.deleteFromFavorites(products, userId)
        _favoriteState.value = FavoriteState.Loading
        getFavorites()
    }

    fun clearAllFavorites() {
        viewModelScope.launch {
            favoritesRepository.clearAllFavorites(userId)
            _favoriteState.value = FavoriteState.Loading
        }
        getFavorites()
    }
}

sealed interface FavoriteState {
    object Loading : FavoriteState
    data class SuccessFavoriteState(val products: List<ProductUI>) : FavoriteState
    data class EmptyScreen(val failMessage: String) : FavoriteState
    data class ShowPopUp(val errorMessage: String) : FavoriteState
}