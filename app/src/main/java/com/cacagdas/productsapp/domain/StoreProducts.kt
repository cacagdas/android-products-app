package com.cacagdas.productsapp.domain

import com.cacagdas.productsapp.core.base.usecase.RequestUseCase
import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.data.repo.ProductRepository
import javax.inject.Inject

class StoreProducts @Inject constructor(
    private val repository: ProductRepository,
) : RequestUseCase<StoreProducts.Params, Unit>() {

    data class Params(val items: List<Product>)

    override suspend fun buildRequest(params: Params) = repository.storeProducts(params.items)
}
