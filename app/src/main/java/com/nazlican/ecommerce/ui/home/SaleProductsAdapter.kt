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
    RecyclerView.Adapter<SaleProductsAdapter.SaleProductRowHolder>() {
    private val saleProductList = ArrayList<ProductUI>()

    inner class SaleProductRowHolder(private val binding: ItemViewSaleProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productUI: ProductUI) {
            binding.apply {

                productUI.imageOne.let { saleProductIv.downloadFromUrl(it) }
                saleProductNameTv.text = productUI.title
                saleProductRatingBar.rating = productUI.rate.toFloat()

                    if (productUI.salePrice != null) {
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
                        priceTv.paintFlags = 0
                    }

                    root.setOnClickListener {
                        onItemClickListener.invoke(productUI.id)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleProductRowHolder {
        val binding =
            ItemViewSaleProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleProductRowHolder(binding)
    }

    override fun getItemCount(): Int {
        return saleProductList.size
    }

    override fun onBindViewHolder(holder: SaleProductRowHolder, position: Int) {
        val product = saleProductList[position]
        holder.bind(product)
    }

    fun updateList(updateList: List<ProductUI>) {
        saleProductList.clear()
        saleProductList.addAll(updateList)
        notifyDataSetChanged()
    }
}