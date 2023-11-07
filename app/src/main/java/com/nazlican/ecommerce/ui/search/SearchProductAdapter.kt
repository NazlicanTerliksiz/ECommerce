package com.nazlican.ecommerce.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.response.Product
import com.nazlican.ecommerce.databinding.ItemViewSearchProductsBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class SearchProductsAdapter(
    private val onItemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<SearchProductsAdapter.RowHolder>() {
    private val searchProductList = ArrayList<Product>()

    inner class RowHolder(private val binding: ItemViewSearchProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                searchProductTv.text = product.title
                product.imageOne?.let { searchProductIv.downloadFromUrl(it) }
                root.setOnClickListener {
                    onItemClickListener.invoke(product.id)
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

    fun updateList(updateList:List<Product>){
        searchProductList.clear()
        searchProductList.addAll(updateList)
        notifyDataSetChanged()
    }
}