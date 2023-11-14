package com.nazlican.ecommerce.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentPaymentBinding

import com.nazlican.sisterslabproject.common.viewBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private val viewModel: PaymentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.paymentButton.setOnClickListener {
            with(binding) {
                viewModel.checkFields(
                    paymentNameEt.text.toString(),
                    paymentCartNumberEt.text.toString(),
                    paymentMonthEt.text.toString(),
                    paymentYearEt.text.toString(),
                    paymentCvvEt.text.toString(),
                    paymentAddressEt.text.toString()
                )
            }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        paymentObserve()
    }

    private fun paymentObserve() =
        viewModel.paymentState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PaymentState.PaymentSuccessState -> {
                    findNavController().navigate(R.id.paymentToSuccessScreen)
                }

                is PaymentState.ShowPopUp -> {
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
}
