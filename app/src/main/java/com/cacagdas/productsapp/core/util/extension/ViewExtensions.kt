package com.cacagdas.productsapp.core.util.extension

import android.view.View

fun View.visibleOrGone(visible: Boolean) {
    if (visible) visible()
    else gone()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}