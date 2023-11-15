package com.nazlican.ecommerce.ui.home

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.databinding.ItemViewProductsBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class MainProductsAdapter(
    private val onItemClickListener: (Int) -> Unit,
    private val onFavClick: (ProductUI) -> Unit
) :
    RecyclerView.Adapter<MainProductsAdapter.MainProductRowHolder>() {
    private val productList = ArrayList<ProductUI>()

    inner class MainProductRowHolder(private val binding: ItemViewProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productUI: ProductUI) {
            binding.apply {

                productUI.imageOne.let { productIv.downloadFromUrl(it) }
                productTv.text = productUI.title
                productRatingBar.rating = productUI.rate.toFloat()

                if (productUI.saleState) {
                    salePriceTv.text = "${productUI.salePrice} ₺"
                    val originalPrice = "${productUI.price} ₺"
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
                    priceTv.text = "${productUI.price} ₺"
                    salePriceTv.visibility = View.GONE
                }

                root.setOnClickListener {
                    onItemClickListener.invoke(productUI.id)
                    salePriceTv.visibility = View.GONE
                }

                favoriteIv.setOnClickListener {
                    onFavClick(productUI)
                }

                favoriteIv.setBackgroundResource(
                    if (productUI.isFav) R.drawable.ic_fav_selected
                    else R.drawable.ic_fav_unselected
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductRowHolder {
        val binding =
            ItemViewProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainProductRowHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MainProductRowHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

    }

    fun updateList(updateList: List<ProductUI>) {
        productList.clear()
        productList.addAll(updateList)
        notifyDataSetChanged()
    }
}