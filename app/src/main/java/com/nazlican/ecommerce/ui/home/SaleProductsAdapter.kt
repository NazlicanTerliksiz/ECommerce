package com.nazlican.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.response.ProductListUI
import com.nazlican.ecommerce.databinding.ItemViewSaleProductsBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class SaleProductsAdapter(
    private val onItemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<SaleProductsAdapter.RowHolder>() {
    private val saleProductList = ArrayList<ProductListUI>()

    inner class RowHolder(private val binding: ItemViewSaleProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductListUI) {
            binding.apply {
                saleProductTv.text = product.title
                salePriceTv.text = product.price.toString()
                ratingBar.rating = product.rate?.toFloat() ?: 4.2f
                product.imageOne?.let { saleProductIv.downloadFromUrl(it) }
                root.setOnClickListener {
                    onItemClickListener.invoke(product.id)
               }
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
    fun updateList(updateList:List<ProductListUI>){
        saleProductList.clear()
        saleProductList.addAll(updateList)
        notifyDataSetChanged()
    }
}