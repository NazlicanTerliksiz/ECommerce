package com.nazlican.ecommerce.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.request.ClearCart
import com.nazlican.ecommerce.data.model.request.DeleteFromCart
import com.nazlican.ecommerce.databinding.FragmentCartBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.snackbar
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

        cartProductAdapter = CartProductAdapter(::homeToDetail) {
            viewModel.deleteFromCart(DeleteFromCart(it, userId))
        }

        binding.cartProductRv.adapter = cartProductAdapter
        viewModel.getCartProduct(userId)
        cartProductObserve()

        binding.cartClearAllCartIv.setOnClickListener {
            viewModel.clearCart(ClearCart(userId))
        }

        binding.cartPayNowButton.setOnClickListener {
            findNavController().navigate(R.id.cartToPayment)
        }
    }

    private fun cartProductObserve() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                CartState.Loading -> {
                    cartProgressBar.visible()
                }

                is CartState.CartProductSuccessState -> {
                    cartProgressBar.gone()
                    cartProductAdapter.updateList(state.products)
                    val totalPrice = viewModel.totalPrice(state.products)
                    binding.cartRroductsTotalPriceTv.text = "Total price : $totalPrice ₺"
                }

                is CartState.DeleteProductSuccessState -> {
                    cartProgressBar.gone()
                    view?.snackbar("Product deleted!")
                    Snackbar.make(requireView(), "Product deleted!", Snackbar.LENGTH_SHORT).show()
                    viewModel.getCartProduct(FirebaseAuth.getInstance().currentUser!!.uid)
                }

                is CartState.ClearCartSuccessState -> {
                    cartProgressBar.gone()
                    view?.snackbar("Product All deleted!")
                    viewModel.getCartProduct(FirebaseAuth.getInstance().currentUser!!.uid)
                }

                is CartState.EmptyScreen -> {
                    cartProgressBar.gone()
                    cartEmptyIv.visible()
                    cartEmptyTv.visible()
                    cartEmptyTv.text = state.failMessage
                    cartProductRv.gone()
                }

                is CartState.ShowPopUp -> {
                    cartProgressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

    private fun homeToDetail(id: Int) {
        val action = CartFragmentDirections.cartToDetail(id)
        findNavController().navigate(action)
    }
}