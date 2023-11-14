package com.nazlican.ecommerce.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.request.ClearCart
import com.nazlican.ecommerce.databinding.FragmentPaymentBinding
import com.nazlican.ecommerce.ui.cart.CartViewModel
import com.nazlican.ecommerce.util.extensions.snackbar
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private val paymentViewModel: PaymentViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.paymentButton.setOnClickListener {
            with(binding) {
                paymentViewModel.checkFields(
                    paymentNameEt.text.toString(),
                    paymentCartNumberEt.text.toString(),
                    paymentMonthEt.text.toString(),
                    paymentYearEt.text.toString(),
                    paymentCvvEt.text.toString(),
                    paymentAddressEt.text.toString()
                )
            }
            cartViewModel.clearCart(ClearCart(FirebaseAuth.getInstance().currentUser!!.uid))
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        paymentObserve()
    }

    private fun paymentObserve() =
        paymentViewModel.paymentState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PaymentState.PaymentSuccessState -> {
                    findNavController().navigate(R.id.paymentToSuccessScreen)
                }

                is PaymentState.ShowPopUp -> {
                    view?.snackbar(state.errorMessage)
                }
            }
        }
}
