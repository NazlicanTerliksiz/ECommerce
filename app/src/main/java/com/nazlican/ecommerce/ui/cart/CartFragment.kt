package com.nazlican.ecommerce.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentCartBinding
import com.nazlican.sisterslabproject.common.viewBinding

class CartFragment : Fragment() {

    private val binding by viewBinding(FragmentCartBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

}