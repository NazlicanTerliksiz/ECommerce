package com.nazlican.ecommerce.util.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.downloadFromUrl(url: String){
    Glide.with(this)
        .load(url)
        .into(this)
}