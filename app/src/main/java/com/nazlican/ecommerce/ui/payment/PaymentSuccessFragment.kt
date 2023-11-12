package com.nazlican.ecommerce.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentPaymentSuccessBinding
import com.nazlican.sisterslabproject.common.viewBinding

class PaymentSuccessFragment : Fragment(R.layout.fragment_payment_success) {

    private val binding by viewBinding(FragmentPaymentSuccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continieBotton.setOnClickListener {
            findNavController().navigate(R.id.successScreenToHome)
        }
    }

}