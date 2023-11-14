package com.nazlican.ecommerce.ui.productDetail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.request.AddToCart
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.databinding.FragmentProductDetailBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val binding by viewBinding(FragmentProductDetailBinding::bind)
    private val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var imageList: ArrayList<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.id
        viewModel.getDetailProduct(id)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewPager2= binding.detailProductViewPager

        with(binding) {
            AddToCartbutton.setOnClickListener {
                viewModel.addToCartProduct(AddToCart(userId, id))
            }
            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        setUpTransformer()
        detailProductObserve()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
            }
        })


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
                    detailProductRatingBar.rating = state.detailResponse.rate.toFloat()
                    priceTv.text = state.detailResponse.price.toString()

                   init(state.detailResponse)

                    if (state.detailResponse.saleState) {
                        salePriceTv.text = state.detailResponse.salePrice.toString()
                        val originalPrice = state.detailResponse.price.toString()
                        val spannableString = SpannableString(originalPrice)
                        spannableString.setSpan(
                            StrikethroughSpan(),
                            0,
                            originalPrice.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        priceTv.text = spannableString
                        priceTv.visibility = View.VISIBLE
                    } else {
                        priceTv.text = state.detailResponse.price.toString()
                        salePriceTv.visibility = View.GONE
                    }

                    favoriteIv.setBackgroundResource(
                        if (state.detailResponse.isFav) R.drawable.ic_fav_selected
                        else R.drawable.ic_fav_unselected
                    )

                    favoriteIv.setOnClickListener {
                        viewModel.setFavoriteState(state.detailResponse)
                    }
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

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }
    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(50))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
    }
    private fun init(product:ProductUI) {
        //viewPager2 = binding.detailProductViewPager
        handler = Handler(Looper.myLooper()!!)

        imageList = ArrayList()
        with(imageList) {
            add(product.imageOne)
            add(product.imageThree)
            add(product.imageTwo)
        }
        viewPagerAdapter = ViewPagerAdapter(imageList, viewPager2)

        with(viewPager2) {
            adapter = viewPagerAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
}
