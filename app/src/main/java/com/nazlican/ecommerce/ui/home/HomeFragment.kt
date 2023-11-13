package com.nazlican.ecommerce.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.databinding.FragmentHomeBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var mainProductsAdapter: MainProductsAdapter
    private lateinit var saleProductsAdapter: SaleProductsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()
        viewModel.getSaleProducts()
        viewModel.getCategoryName()
        mainProductObserve()


        with(binding) {
            mainProductsAdapter = MainProductsAdapter(::homeToDetail, ::homeToFavorite)
            homeMainProductRv.adapter = mainProductsAdapter
            saleProductsAdapter = SaleProductsAdapter(::homeToDetail, ::homeToFavorite)
            homeSaleProductRv.adapter = saleProductsAdapter
            categoryAdapter = CategoryAdapter(::getCategory)
            binding.homeCategoryRv.adapter = categoryAdapter
        }
    }

    private fun mainProductObserve() = with(binding) {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> {
                    homeProgressBar.visible()
                }

                is HomeState.SuccessProductState -> {
                    homeProgressBar.gone()
                    homeEmptyIv.gone()
                    homeEmptyTv.gone()
                    mainProductsAdapter.updateList(state.products)
                    Log.e("message", state.products.toString())
                }

                is HomeState.SuccessSaleProductState -> {
                    homeProgressBar.gone()
                    homeEmptyIv.gone()
                    homeEmptyTv.gone()
                    saleProductsAdapter.updateList(state.products)
                    Log.e("salemessage", state.products.toString())
                }

                is HomeState.SuccessCategoryNameState -> {
                    homeProgressBar.gone()
                    homeEmptyIv.gone()
                    homeEmptyTv.gone()
                    val addCategory: MutableList<String> = mutableListOf()
                    addCategory.add("All")
                    addCategory.addAll(state.category)
                    categoryAdapter.updateList(addCategory)
                }

                is HomeState.SuccessCategoryProductState -> {
                    homeProgressBar.gone()
                    homeEmptyIv.gone()
                    homeEmptyTv.gone()
                    mainProductsAdapter.updateList(state.products)
                }

                is HomeState.EmptyScreen -> {
                    homeProgressBar.gone()
                    homeEmptyIv.visible()
                    homeEmptyTv.visible()
                    homeEmptyTv.text = state.failMessage
                }

                is HomeState.ShowPopUp -> {
                    homeProgressBar.gone()
                    productConstraintLayout.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun homeToDetail(id: Int) {
        val action = HomeFragmentDirections.homeToDetail(id)
        findNavController().navigate(action)
    }

    private fun getCategory(category: String) {
        if (category == "All") {
            viewModel.getProducts()
        }
        viewModel.getProductsByCategory(category)
    }

    private fun homeToFavorite(product: ProductUI){
        viewModel.setFavoriteState(product)
    }

}