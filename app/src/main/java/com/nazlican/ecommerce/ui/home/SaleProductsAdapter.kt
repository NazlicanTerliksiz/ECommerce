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
import com.nazlican.ecommerce.databinding.ItemViewSaleProductsBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class SaleProductsAdapter(
    private val onItemClickListener: (Int) -> Unit,
    private val onFavClick: (ProductUI) -> Unit
) :
    RecyclerView.Adapter<SaleProductsAdapter.RowHolder>() {
    private val saleProductList = ArrayList<ProductUI>()

    inner class RowHolder(private val binding: ItemViewSaleProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUI) {
            binding.apply {

                product.imageOne.let { saleProductIv.downloadFromUrl(it) }
                saleProductNameTv.text = product.title
                saleProductRatingBar.rating = product.rate.toFloat()

                if (product.salePrice != null) {
                    salePriceTv.text = product.salePrice.toString()
                    val originalPrice = product.price.toString()
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
                    priceTv.text = product.price.toString()
                    priceTv.paintFlags = 0
                }

                root.setOnClickListener {
                    onItemClickListener.invoke(product.id)
                }
                favoriteIv.setOnClickListener {
                    onFavClick(product)
                }

                favoriteIv.setBackgroundResource(
                    if (product.isFav) R.drawable.ic_fav_selected
                    else R.drawable.ic_fav_unselected
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding =
            ItemViewSaleProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return saleProductList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val product = saleProductList[position]
        holder.bind(product)
    }

    fun updateList(updateList: List<ProductUI>) {
        saleProductList.clear()
        saleProductList.addAll(updateList)
        notifyDataSetChanged()
    }
}