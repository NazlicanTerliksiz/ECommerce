package com.nazlican.ecommerce.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.databinding.FragmentFavoritesBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorites()

        with(binding) {
            favoritesAdapter = FavoritesAdapter(::homeToDetail, ::deleteFromFavorites)
            favoritesProductRv.adapter = favoritesAdapter

            clearAllFavoritesIv.setOnClickListener {
                viewModel.clearAllFavorites()
            }
        }

        favoriteProductObserve()
    }

    private fun favoriteProductObserve() = with(binding) {
        viewModel.favoriteState.observe(viewLifecycleOwner) { state ->
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
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun homeToDetail(id: Int) {
        val action = FavoritesFragmentDirections.favoriteToDetail(id)
        findNavController().navigate(action)
    }

    private fun deleteFromFavorites(product: ProductUI) {
        viewModel.deleteFromFavorites(product)
    }

}


