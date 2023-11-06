package com.nazlican.ecommerce.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nazlican.ecommerce.R
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
            mainProductsAdapter = MainProductsAdapter(::homeToDetail)
            productRv.adapter = mainProductsAdapter
            saleProductsAdapter = SaleProductsAdapter(::homeToDetail)
            saleProductRv.adapter = saleProductsAdapter
            categoryAdapter = CategoryAdapter(::getCategory)
            binding.categoryRv.adapter = categoryAdapter
        }
    }

    private fun mainProductObserve() = with(binding) {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> {
                    progressBar.visible()
                    productConstraintLayout.gone()
                }

                is HomeState.SuccessProductState -> {
                    progressBar.gone()
                    productConstraintLayout.visible()
                    mainProductsAdapter.updateList(state.products)
                    Log.e("message", state.products.toString())
                }

                is HomeState.SuccessSaleProductState -> {
                    progressBar.gone()
                    productConstraintLayout.visible()
                    saleProductsAdapter.updateList(state.products)
                    Log.e("salemessage", state.products.toString())
                }

                is HomeState.SuccessCategoryNameState -> {
                    progressBar.gone()
                    productConstraintLayout.visible()
                    val addCategory: MutableList<String> = mutableListOf()
                    addCategory.add("All")
                    addCategory.addAll(state.category)
                    categoryAdapter.updateList(addCategory)
                }

                is HomeState.SuccessCategoryProductState -> {
                    progressBar.gone()
                    productConstraintLayout.visible()
                    mainProductsAdapter.updateList(state.products)
                }

                is HomeState.EmptyScreen -> {
                    progressBar.gone()
                    productConstraintLayout.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is HomeState.ShowPopUp -> {
                    progressBar.gone()
                    productConstraintLayout.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun homeToDetail(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(id)
        findNavController().navigate(action)
    }

    private fun getCategory(category: String) {
        if (category == "All") {
            viewModel.getProducts()
        }
        viewModel.getProductsByCategory(category)
    }

}