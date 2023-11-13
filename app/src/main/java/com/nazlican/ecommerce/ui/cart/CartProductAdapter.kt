package com.nazlican.ecommerce.ui.cart

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
class CartProductAdapter(
    private val onDetailClickListener: (Int) -> Unit,
    private val onDeleteClickListener: (Int) -> Unit
):
    RecyclerView.Adapter<CartProductAdapter.CartProductRowHolder>() {
    private val cartProductList = ArrayList<ProductUI>()

    inner class CartProductRowHolder(private val binding: ItemViewCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productUI: ProductUI) {
            binding.apply {

                cartProductIv.downloadFromUrl(productUI.imageOne)
                cartProductNameTv.text = productUI.title

                if (productUI.saleState == true){
                    if(productUI.salePrice != null) {
                        salePriceTv.text = productUI.salePrice.toString()
                        val originalPrice = productUI.price.toString()
                        val spannableString = SpannableString(originalPrice)
                        spannableString.setSpan(StrikethroughSpan(), 0, originalPrice.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        priceTv.text = spannableString
                        priceTv.visibility = View.VISIBLE
                    } else {
                        priceTv.text = productUI.price.toString()
                        priceTv.paintFlags = 0
                    }

                }else{
                    priceTv.text = productUI.price.toString()
                    salePriceTv.visibility = View.GONE
                }

                root.setOnClickListener{
                    onDetailClickListener.invoke(productUI.id)
                }
                cartDeleteIv.setOnClickListener {
                    onDeleteClickListener.invoke(productUI.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductRowHolder {
        val binding =
            ItemViewCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductRowHolder(binding)
    }

    override fun onBindViewHolder(holder: CartProductRowHolder, position: Int) {
        val favoriteProduct = cartProductList[position]
        holder.bind(favoriteProduct)
    }

    override fun getItemCount(): Int {
        return cartProductList.size
    }

    fun updateList(updateList:List<ProductUI>){
        cartProductList.clear()
        cartProductList.addAll(updateList)
        notifyDataSetChanged()
    }

}