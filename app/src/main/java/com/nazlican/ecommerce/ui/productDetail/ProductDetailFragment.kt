package com.nazlican.ecommerce.ui.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.AddToCart
import com.nazlican.ecommerce.databinding.FragmentProductDetailBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.visible
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
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        binding.AddToCartbutton.setOnClickListener {
            viewModel.AddToCartProduct(AddToCart(userId, id))
        }
    }

    private fun detailProductObserve() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->

            when (state) {
                DetailState.Loading -> {
                    progressBar.visible()
                    productDetailConstraintLayout.gone()
                }

                is DetailState.SuccessState -> {
                    progressBar.gone()
                    productDetailConstraintLayout.visible()
                    productDetailtv.text = state.detailResponse.title
                    descriptionTv.text = state.detailResponse.description
                    ratingBar.rating = state.detailResponse.rate?.toFloat() ?: 4.2f
                    priceTv.text = state.detailResponse.price.toString()
                    productDetailIv.downloadFromUrl(state.detailResponse.imageOne)
                }

                is DetailState.SuccessAddToCartState -> {
                    findNavController().popBackStack()
                }

                is DetailState.EmptyScreen -> {
                    progressBar.gone()
                    productDetailConstraintLayout.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is DetailState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}
