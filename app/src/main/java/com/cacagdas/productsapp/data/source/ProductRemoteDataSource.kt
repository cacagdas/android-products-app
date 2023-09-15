package com.cacagdas.productsapp.data.source

import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val service: ProductService,
) {
    suspend fun getProducts() = service.getProducts()

    suspend fun getProductDetail(id: Long) = service.getProductDetail(id)
}
