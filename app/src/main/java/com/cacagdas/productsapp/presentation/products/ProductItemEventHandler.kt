package com.cacagdas.productsapp.presentation.products

import com.cacagdas.productsapp.data.model.Product

interface ProductItemEventHandler {
    fun onProductClick(product: Product)
}
