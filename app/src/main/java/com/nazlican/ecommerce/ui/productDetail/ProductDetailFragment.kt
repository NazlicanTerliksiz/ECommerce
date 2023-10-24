package com.nazlican.ecommerce.ui.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentProductDetailBinding
import com.nazlican.ecommerce.util.downloadFromUrl
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val binding by viewBinding(FragmentProductDetailBinding::bind)
    private val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.id
        viewModel.getDetailProduct(id)
        detailProductObserve()
    }

    private fun detailProductObserve() {
        viewModel.detailProductLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.productDetailtv.text = it.title
                binding.descriptionTv.text = it.description
                binding.priceTv.text = it.price.toString()
                binding.productDetailIv.downloadFromUrl(it.imageOne)
            } else {
                Snackbar.make(requireView(), "liste boş", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
