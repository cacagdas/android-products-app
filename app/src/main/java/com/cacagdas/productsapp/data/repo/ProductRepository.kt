package com.cacagdas.productsapp.data.repo

import com.cacagdas.productsapp.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun storeProducts(items: List<Product>)

    fun getProducts(): Flow<List<Product>>

    suspend fun getProductDetail(id: String): Product
}
