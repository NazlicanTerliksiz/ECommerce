package com.nazlican.ecommerce.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentSearchBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchFragmentViewModel by viewModels()
    private lateinit var searchProductAdapter: SearchProductsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.productRv.layoutManager = LinearLayoutManager(requireContext())
        searchProductAdapter = SearchProductsAdapter(::homeToDetail)
        binding.searchProductRv.adapter = searchProductAdapter
        searchProductObserve()
        search()

    }

    private fun searchProductObserve() = with(binding) {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {

                SearchState.Loading -> searchProgressBar.visible()

                is SearchState.SuccessSearchState -> {
                    searchProgressBar.gone()
                    searchProductAdapter.updateList(state.products)
                }

                is SearchState.EmptyScreen -> {
                    searchProgressBar.gone()
                    searchEmptyIv.visible()
                    searchEmptyTv.visible()
                    searchEmptyTv.text = state.failMessage
                }

                is SearchState.ShowPopUp -> {
                    searchProgressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun homeToDetail(id: Int) {
        val action = SearchFragmentDirections.searchToDetail(id)
        findNavController().navigate(action)
    }

    fun search() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchProduct(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != null && newText.length>3){
                    viewModel.searchProduct(newText)
                }else{
                    searchProductAdapter.updateList(emptyList())
                }
                return true
            }

        })
    }
}
