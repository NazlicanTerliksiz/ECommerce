package com.nazlican.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.databinding.ItemViewProductsBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class MainProductsAdapter(
    private val onItemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<MainProductsAdapter.RowHolder>() {
    private val productList =  ArrayList<Product>()

    inner class RowHolder(private val binding: ItemViewProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                productTv.text = product.title
                priceTv.text = product.price.toString()
                ratingBar.rating = product.rate?.toFloat() ?: 4.2f
                product.imageOne?.let { productIv.downloadFromUrl(it) }
                root.setOnClickListener {
                    onItemClickListener.invoke(product.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding =
            ItemViewProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

    }
    fun updateList(updateList:List<Product>){
        productList.clear()
        productList.addAll(updateList)
        notifyDataSetChanged()
    }
}