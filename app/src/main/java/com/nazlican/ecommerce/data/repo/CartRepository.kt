package com.nazlican.ecommerce.data.repo

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.data.model.AddToCart
import com.nazlican.ecommerce.data.model.DeleteFromCart
import com.nazlican.ecommerce.data.model.Product
import com.nazlican.ecommerce.data.source.remote.ProductService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CartRepository(private val productService: ProductService) {

    private var job: Job? = null

    val cartProductsLiveData = MutableLiveData<List<Product>?>()
    val deleteProductsLiveData = MutableLiveData<DeleteFromCart?>()
    var addToCartLiveData = MutableLiveData<AddToCart?>()


    fun cartProducts(userId: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result =productService.getCartProducts(userId)
            if (result.isSuccessful) {
                result.body()?.let {cartProductList ->
                    cartProductsLiveData.postValue(cartProductList.products)
                }
            }else{
                cartProductsLiveData.postValue(null)
            }
        }
    }

    fun addToCart(addToCart: AddToCart) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result =productService.addToCart(addToCart)
            if (result.isSuccessful) {
                result.body()?.let {
                    addToCartLiveData.postValue(result.body())
                }
            }else{
                addToCartLiveData.postValue(null)
            }
        }
    }

    fun deleteFromCart(deleteFromCart: DeleteFromCart) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        job = CoroutineScope(Dispatchers.IO).launch {
            val result =productService.deleteFromCart(deleteFromCart)
            if (result.isSuccessful) {
                result.body()?.let {cartProductList ->
                    cartProducts(userId)
                    deleteProductsLiveData.postValue(result.body())
                }
            }else{
                deleteProductsLiveData.postValue(null)
            }
        }
    }
}