package com.nazlican.ecommerce.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.DeleteFromCart
import com.nazlican.ecommerce.databinding.FragmentCartBinding
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartProductAdapter: CartProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cartProductRv.layoutManager = LinearLayoutManager(requireContext())
        cartProductAdapter = CartProductAdapter(::deleteFromCart)
        binding.cartProductRv.adapter = cartProductAdapter
        viewModel.getCartProduct()
        cartProductbserve()
        deleteFromCartObserve()
    }

    private fun cartProductbserve() {
        viewModel.cartProductsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                cartProductAdapter.updateList(it)
            } else {
                Snackbar.make(requireView(), "list is empty", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteFromCartObserve() {
        viewModel.deleteProductsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Snackbar.make(requireView(), "Product deleted!", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(
                    requireView(),
                    "The product could not be deleted.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    fun deleteFromCart(id: Int) {
        viewModel.deleteFromCart(DeleteFromCart(id))
    }

}