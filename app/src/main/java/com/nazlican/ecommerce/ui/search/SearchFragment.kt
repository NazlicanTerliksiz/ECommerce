package com.nazlican.ecommerce.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentSearchBinding
import com.nazlican.sisterslabproject.common.viewBinding

class SearchFragment : Fragment() {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

}