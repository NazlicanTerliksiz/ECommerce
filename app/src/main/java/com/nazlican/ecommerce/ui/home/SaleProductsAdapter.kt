package com.nazlican.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.databinding.ItemViewSaleProductsBinding
import com.nazlican.ecommerce.util.downloadFromUrl

class SaleProductsAdapter(
    private val saleProductList: List<Product>,
    private val onItemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<SaleProductsAdapter.RowHolder>() {

    inner class RowHolder(private val binding: ItemViewSaleProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                saleProductTv.text = product.title
                salePriceTv.text = product.price.toString()
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
}