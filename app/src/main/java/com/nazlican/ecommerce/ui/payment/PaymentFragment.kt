package com.nazlican.ecommerce.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentPaymentBinding
import com.nazlican.sisterslabproject.common.viewBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.paymentButton.setOnClickListener {
            findNavController().navigate(R.id.paymentToSuccessScreen)
        }
    }
}