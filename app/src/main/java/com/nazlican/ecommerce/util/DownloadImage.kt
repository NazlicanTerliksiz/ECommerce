package com.nazlican.ecommerce.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.dowloadFromUrl(url: String) {
    Picasso.get().load(url).into(this)
}
