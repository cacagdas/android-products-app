package com.cacagdas.productsapp.domain

import com.cacagdas.productsapp.core.base.usecase.RequestUseCase
import com.cacagdas.productsapp.data.model.ProductsResponse
import com.cacagdas.productsapp.data.repo.ProductRepository
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val repository: ProductRepository,
) : RequestUseCase<GetProducts.Params, ProductsResponse>() {

    data class Params(val unit: Unit)

    override suspend fun buildRequest(params: Params) = repository.getProducts()
}
