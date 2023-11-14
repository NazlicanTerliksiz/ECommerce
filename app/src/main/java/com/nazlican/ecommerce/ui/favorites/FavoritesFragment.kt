package com.nazlican.ecommerce.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.request.AddToCart
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.databinding.FragmentFavoritesBinding
import com.nazlican.ecommerce.ui.productDetail.ProductDetailViewModel
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.snackbar
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.getFavorites()

        with(binding) {
            favoritesAdapter =
                FavoritesAdapter(::homeToDetail, ::deleteFromFavorites, ::addToCartFromFavorite)
            favoritesProductRv.adapter = favoritesAdapter

            clearAllFavoritesIv.setOnClickListener {
                favoriteViewModel.clearAllFavorites()
            }
        }
        favoriteProductObserve()
    }

    private fun favoriteProductObserve() = with(binding) {
        favoriteViewModel.favoriteState.observe(viewLifecycleOwner) { state ->
            when (state) {
                FavoriteState.Loading -> {
                    favoriteProgressBar.visible()
                }

                is FavoriteState.SuccessFavoriteState -> {
                    favoriteProgressBar.gone()
                    favoritesAdapter.updateList(state.products)
                }

                is FavoriteState.EmptyScreen -> {
                    favoriteProgressBar.gone()
                    favoriteEmptyIv.visible()
                    favoriteEmptyTv.visible()
                    favoritesProductRv.gone()
                    favoriteEmptyTv.text = state.failMessage
                }

                is FavoriteState.ShowPopUp -> {
                    favoriteProgressBar.gone()
                    view?.snackbar(state.errorMessage)
                }
            }
        }
    }

    private fun homeToDetail(id: Int) {
        val action = FavoritesFragmentDirections.favoriteToDetail(id)
        findNavController().navigate(action)
    }

    private fun deleteFromFavorites(product: ProductUI) {
        favoriteViewModel.deleteFromFavorites(product)
    }

    private fun addToCartFromFavorite(addToCart: AddToCart) {
        productDetailViewModel.addToCartProduct(addToCart)
        view?.snackbar("product added to cart")
    }
}


