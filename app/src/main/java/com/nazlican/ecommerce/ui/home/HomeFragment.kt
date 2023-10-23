package com.nazlican.ecommerce.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
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
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()
        viewModel.getSaleProducts()
        mainProductObserve()
        saleProductObserve()
    }

    private fun mainProductObserve() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                mainProductsAdapter = MainProductsAdapter(it)
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
                saleProductsAdapter = SaleProductsAdapter(it)
                binding.saleProductRv.adapter = saleProductsAdapter
                saleProductsAdapter.notifyDataSetChanged()
            } else {
                Snackbar.make(requireView(), "liste boş", Snackbar.LENGTH_LONG).show()
            }
        }
    }

}