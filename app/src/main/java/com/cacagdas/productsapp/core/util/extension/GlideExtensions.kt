package com.cacagdas.productsapp.core.util.extension

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cacagdas.productsApp.R

fun ImageView.loadImage(uri: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_shopping_cart)
        .fallback(ColorDrawable(Color.GRAY))
        .error(ColorDrawable(Color.GRAY))
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}