package com.cacagdas.productsapp.data.source.remote

import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val service: ProductService,
) {
    suspend fun getProducts() = service.getProducts().products

    suspend fun getProductDetail(id: String) = service.getProductDetail(id)
}
