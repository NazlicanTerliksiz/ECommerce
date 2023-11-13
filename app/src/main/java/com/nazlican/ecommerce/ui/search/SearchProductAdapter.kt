package com.nazlican.ecommerce.ui.search

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.databinding.ItemViewSearchProductsBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class SearchProductsAdapter(
    private val onDetailClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<SearchProductsAdapter.RowHolder>() {
    private val searchProductList = ArrayList<ProductUI>()

    inner class RowHolder(private val binding: ItemViewSearchProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productUI: ProductUI) {
            binding.apply {

                searchProductTv.text = productUI.title
                ratingBar.rating = productUI.rate.toFloat()
                productUI.imageOne.let { searchProductIv.downloadFromUrl(it) }

                if (productUI.saleState == true){
                    if(productUI.salePrice != null) {
                        salePriceTv.text = productUI.salePrice.toString()
                        val originalPrice = productUI.price.toString()
                        val spannableString = SpannableString(originalPrice)
                        spannableString.setSpan(StrikethroughSpan(), 0, originalPrice.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        searchPriceTv.text = spannableString
                        searchPriceTv.visibility = View.VISIBLE
                    } else {
                        searchPriceTv.text = productUI.price.toString()
                        searchPriceTv.paintFlags = 0
                    }
                }else{
                    searchPriceTv.text = productUI.price.toString()
                    salePriceTv.visibility = View.GONE
                }

                root.setOnClickListener {
                    onDetailClickListener.invoke(productUI.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding =
            ItemViewSearchProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return searchProductList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val searchProduct = searchProductList[position]
        holder.bind(searchProduct)

    }

    fun updateList(updateList:List<ProductUI>){
        searchProductList.clear()
        searchProductList.addAll(updateList)
        notifyDataSetChanged()
    }
}