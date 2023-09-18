package com.cacagdas.productsapp.data.repo

import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.data.model.ProductsResponse

interface ProductRepository {

    suspend fun storeProducts(items: List<Product>)

    suspend fun getLocalProducts(): List<Product>

    suspend fun getLocalProductDetail(id: String): Product

    suspend fun getProducts(): ProductsResponse

    suspend fun getProductDetail(id: String): Product
}
