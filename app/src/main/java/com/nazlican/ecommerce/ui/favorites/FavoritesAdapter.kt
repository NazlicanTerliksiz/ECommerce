package com.nazlican.ecommerce.ui.favorites

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazlican.ecommerce.data.model.request.AddToCart
import com.nazlican.ecommerce.data.model.response.ProductUI
import com.nazlican.ecommerce.databinding.ItemViewFavoriteProductBinding
import com.nazlican.ecommerce.util.extensions.downloadFromUrl

class FavoritesAdapter(
    private val onDetailClickListener: (Int) -> Unit,
    private val onDeleteClickListener: (ProductUI) -> Unit,
    private val onAddToCartClickListener: (AddToCart) -> Unit
):
    RecyclerView.Adapter<FavoritesAdapter.FavoriteProductRowHolder>() {
    private val favoriteProductList = ArrayList<ProductUI>()

    inner class FavoriteProductRowHolder(private val binding: ItemViewFavoriteProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productUI: ProductUI) {
            binding.apply {

                favoriteProductIv.downloadFromUrl(productUI.imageOne)
                favoriteProductNameTv.text = productUI.title
                ratingBar.rating = productUI.rate.toFloat()

                if (productUI.saleState){
                    salePriceTv.text = "${productUI.salePrice.toString()} ₺"
                    ratingBar.rating = productUI.rate.toFloat()
                    val originalPrice = "${productUI.price.toString()} ₺"
                    val spannableString = SpannableString(originalPrice)
                    spannableString.setSpan(StrikethroughSpan(), 0, originalPrice.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    priceTv.text = spannableString
                    priceTv.visibility = View.VISIBLE
                }else{
                    priceTv.text = "${productUI.price.toString()} ₺"
                    salePriceTv.visibility = View.GONE
                }

                root.setOnClickListener{
                    onDetailClickListener(productUI.id)
                }

                favoriteIv.setOnClickListener {
                    onDeleteClickListener.invoke(productUI)
                }
                addToCartbutton.setOnClickListener {
                    onAddToCartClickListener.invoke(AddToCart(productUI.userId, productUI.id))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductRowHolder {
        val binding =
            ItemViewFavoriteProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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