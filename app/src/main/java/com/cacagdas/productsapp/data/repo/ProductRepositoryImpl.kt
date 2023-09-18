package com.cacagdas.productsapp.data.repo

import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.data.source.local.ProductLocalDataSource
import com.cacagdas.productsapp.data.source.remote.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
) : ProductRepository {

    override suspend fun storeProducts(items: List<Product>) = localDataSource.insertItems(items)

    override suspend fun getLocalProducts() = localDataSource.getProducts()

    override suspend fun getProducts() = remoteDataSource.getProducts()

    override suspend fun getProductDetail(id: String) = remoteDataSource.getProductDetail(id)
}
