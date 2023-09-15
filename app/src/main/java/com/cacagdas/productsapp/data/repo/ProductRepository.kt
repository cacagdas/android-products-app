package com.cacagdas.productsapp.data.repo

import com.cacagdas.productsapp.data.model.Product

interface ProductRepository {

    suspend fun getProducts(): List<Product>

    suspend fun getProductDetail(id: Long): Product
}
