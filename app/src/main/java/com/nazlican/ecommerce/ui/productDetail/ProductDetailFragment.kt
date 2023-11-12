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
import com.nazlican.ecommerce.data.model.request.AddToCart
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

        with(binding) {
            AddToCartbutton.setOnClickListener {
                viewModel.AddToCartProduct(AddToCart(userId, id))
            }
            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    private fun detailProductObserve() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->

            when (state) {
                DetailState.Loading -> {
                    detailProgressBar.visible()
                    productDetailConstraintLayout.gone()
                }

                is DetailState.SuccessState -> {
                    detailProgressBar.gone()
                    productDetailConstraintLayout.visible()
                    detailProductTitleTv.text = state.detailResponse.title
                    detailProductCategoryNameTv.text = state.detailResponse.category
                    detailProductDescriptionTv.text = state.detailResponse.description
                    detailProductRatingBar.rating = state.detailResponse.rate?.toFloat() ?: 4.2f
                    detailProductPriceTv.text = state.detailResponse.price.toString()
                    detailProductIv.downloadFromUrl(state.detailResponse.imageOne)
                }

                is DetailState.SuccessAddToCartState -> {
                    findNavController().popBackStack()
                }

                is DetailState.EmptyScreen -> {
                    detailProgressBar.gone()
                    productDetailConstraintLayout.gone()
                    detailEmptyIv.visible()
                    detailEmptyTv.visible()
                    detailEmptyTv.text = state.failMessage
                }

                is DetailState.ShowPopUp -> {
                    detailProgressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}
