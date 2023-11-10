package com.nazlican.ecommerce.ui.favorites

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.databinding.ItemViewCartProductBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class FavoritesAdapter(
    private val onItemClickListener: (Int) -> Unit,
    private val onDeleteClickListener: (ProductUI) -> Unit
):
    RecyclerView.Adapter<FavoritesAdapter.FavoriteProductRowHolder>() {
    private val favoriteProductList = ArrayList<ProductUI>()

    inner class FavoriteProductRowHolder(private val binding: ItemViewCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteProduct: ProductUI) {
            binding.apply {
                cartProductNameTv.text = favoriteProduct.title
                if (favoriteProduct.saleState == true){
                    if(favoriteProduct.salePrice != null) {
                        salePriceTv.text = favoriteProduct.salePrice.toString()
                        val originalPrice = favoriteProduct.price.toString()
                        val spannableString = SpannableString(originalPrice)
                        spannableString.setSpan(StrikethroughSpan(), 0, originalPrice.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        priceTv.text = spannableString
                        priceTv.visibility = View.VISIBLE
                    } else {
                        priceTv.text = favoriteProduct.price.toString()
                        priceTv.paintFlags = 0
                    }
                }else{
                    priceTv.text = favoriteProduct.price.toString()
                    salePriceTv.visibility = View.GONE
                }
                cartProductIv.downloadFromUrl(favoriteProduct.imageOne)
                root.setOnClickListener{
                    onItemClickListener(favoriteProduct.id)
                }
                deleteIv.setOnClickListener {
                    onDeleteClickListener.invoke(favoriteProduct)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductRowHolder {
        val binding =
            ItemViewCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductRowHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteProductRowHolder, position: Int) {
        val cartProduct = favoriteProductList[position]
        holder.bind(cartProduct)
    }

    override fun getItemCount(): Int {
        return favoriteProductList.size
    }

    fun updateList(updateList:List<ProductUI>){
        favoriteProductList.clear()
        favoriteProductList.addAll(updateList)
        notifyDataSetChanged()
    }

}