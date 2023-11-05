package com.nazlican.ecommerce.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.DeleteFromCart
import com.nazlican.ecommerce.databinding.FragmentCartBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartProductAdapter: CartProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        cartProductAdapter = CartProductAdapter{
            viewModel.deleteFromCart(DeleteFromCart(it),userId)
        }
        binding.cartProductRv.adapter = cartProductAdapter

        viewModel.getCartProduct(userId)
        cartProductObserve()
    }

    private fun cartProductObserve() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                CartState.Loading -> {
                    progressBar.visible()
                }

                is CartState.CartProductSuccessState -> {
                    progressBar.gone()
                    cartProductAdapter.updateList(state.products)
                }

                is CartState.DeleteProductSuccessState -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), "Product deleted!", Snackbar.LENGTH_SHORT).show()
                }
                is CartState.EmptyScreen -> {
                    progressBar.gone()
//                    ivEmpty.visible()
//                    tvEmpty.visible()
//                    tvEmpty.text = state.failMessage
                }

                is CartState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}