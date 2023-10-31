package com.nazlican.ecommerce.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentHomeBinding
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
        saleProductObserve()
        categoryNameObserve()

        categoryAdapter = CategoryAdapter(::getCategory)
        binding.categoryRv.adapter = categoryAdapter

        productByCategoryObserve()
    }

    private fun mainProductObserve() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                mainProductsAdapter = MainProductsAdapter(it, ::homeToDetail)
                binding.productRv.adapter = mainProductsAdapter
                mainProductsAdapter.notifyDataSetChanged()
            } else {
                Snackbar.make(requireView(), "liste boş", Snackbar.LENGTH_LONG).show()
            }
        }
    }
    private fun saleProductObserve(){
        viewModel.saleProductsLiveData.observe(viewLifecycleOwner){
            if (it != null) {
                saleProductsAdapter = SaleProductsAdapter(it, ::homeToDetail)
                binding.saleProductRv.adapter = saleProductsAdapter
                saleProductsAdapter.notifyDataSetChanged()
            } else {
                Snackbar.make(requireView(), "liste boş", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun categoryNameObserve(){
        viewModel.categoryNameLiveData.observe(viewLifecycleOwner){
            if(it != null){
                val addCategory: MutableList<String> = mutableListOf()
                addCategory.add("All")
                addCategory.addAll(it)
                categoryAdapter.updateList(addCategory)
            }
        }
    }

    private fun productByCategoryObserve(){
        viewModel.categoryLiveData.observe(viewLifecycleOwner){
            if (it != null) {
                mainProductsAdapter = MainProductsAdapter(it, ::homeToDetail)
                binding.productRv.adapter = mainProductsAdapter
                mainProductsAdapter.notifyDataSetChanged()
            } else {
                Snackbar.make(requireView(), "liste boş", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun homeToDetail(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(id)
        findNavController().navigate(action)
    }

    private fun getCategory(category: String) {
        viewModel.getProductsByCategory(category)
    }

}