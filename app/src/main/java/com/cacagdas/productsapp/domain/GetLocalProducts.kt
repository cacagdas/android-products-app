package com.cacagdas.productsapp.domain

import com.cacagdas.productsapp.core.base.usecase.RequestUseCase
import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.data.repo.ProductRepository
import javax.inject.Inject

class GetLocalProducts @Inject constructor(
    private val repository: ProductRepository,
) : RequestUseCase<GetLocalProducts.Params, List<Product>>() {

    data class Params(val unit: Unit)

    override suspend fun buildRequest(params: Params) = repository.getLocalProducts()
}
