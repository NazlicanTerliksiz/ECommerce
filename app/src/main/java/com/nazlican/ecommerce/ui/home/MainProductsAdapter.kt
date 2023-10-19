package com.nazlican.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.databinding.ItemViewProductsBinding

class MainProductsAdapter(
    private val productList: List<Product>,
    //private val onItemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<MainProductsAdapter.RowHolder>() {

    inner class RowHolder(private val binding: ItemViewProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                productTv.text = product.title
                priceTv.text = product.price.toString()
            //              productIv.downloadFromUrl(product.imageOne)
//                root.setOnClickListener {
//                    onItemClickListener.invoke(product.id)
//                }
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
}