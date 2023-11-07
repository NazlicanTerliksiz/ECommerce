package com.nazlican.ecommerce.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.response.ProductListUI
import com.nazlican.ecommerce.databinding.ItemViewCartProductBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class CartProductAdapter(
    private val onItemClickListener: (Int) -> Unit
):
    RecyclerView.Adapter<CartProductAdapter.RowHolder>() {
    private val cartProductList = ArrayList<ProductListUI>()

    inner class RowHolder(private val binding: ItemViewCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartProduct: ProductListUI) {
            binding.apply {
                cartProductNameTv.text = cartProduct.title
                cartProductPriceTv.text = cartProduct.price.toString()
                cartProductIv.downloadFromUrl(cartProduct.imageOne.orEmpty())
                deleteIv.setOnClickListener {
                    onItemClickListener.invoke(cartProduct.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding =
            ItemViewCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val cartProduct = cartProductList[position]
        holder.bind(cartProduct)
    }

    override fun getItemCount(): Int {
        return cartProductList.size
    }

    fun updateList(updateList:List<ProductListUI>){
        cartProductList.clear()
        cartProductList.addAll(updateList)
        notifyDataSetChanged()
    }

}