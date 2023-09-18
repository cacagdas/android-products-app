package com.cacagdas.productsapp.data.source.local

import com.cacagdas.productsapp.data.db.ProductDao
import com.cacagdas.productsapp.data.model.Product
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val dao: ProductDao,
) {

    suspend fun insertItems(items: List<Product>) = dao.insertItems(items)

    suspend fun getProducts() = dao.getAllProducts()

    fun getProductDetail(id: String) = dao.getProduct(id)
}
