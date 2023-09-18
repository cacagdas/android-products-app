package com.cacagdas.productsapp.data.repo

import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.data.source.local.ProductLocalDataSource
import com.cacagdas.productsapp.data.source.remote.ProductRemoteDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
) : ProductRepository {

    override suspend fun storeProducts(items: List<Product>) = localDataSource.insertItems(items)

    override fun getProducts(): Flow<List<Product>> {
        //return remoteDataSource.getProducts()
        return callbackFlow {

            trySend(localDataSource.getProducts()).isSuccess

            val remoteProducts = remoteDataSource.getProducts()
            remoteProducts?.let {
                storeProducts(it)
                this.trySend(localDataSource.getProducts()).isSuccess
            } ?: run {
                this.trySend(localDataSource.getProducts()).isSuccess
            }
            awaitClose { cancel() }
        }
    }

    override suspend fun getProductDetail(id: String) = remoteDataSource.getProductDetail(id)
}
