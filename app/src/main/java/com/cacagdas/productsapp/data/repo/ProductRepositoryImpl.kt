package com.cacagdas.productsapp.data.repo

import com.cacagdas.productsapp.data.source.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
) : ProductRepository {

    override suspend fun getProducts() = remoteDataSource.getProducts()

    override suspend fun getProductDetail(id: Long) = remoteDataSource.getProductDetail(id)
}
