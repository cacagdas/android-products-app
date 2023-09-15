package com.cacagdas.productsapp.domain

import com.cacagdas.productsapp.core.base.usecase.RequestUseCase
import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.data.repo.ProductRepository
import javax.inject.Inject

class GetProductDetail @Inject constructor(
    private val repository: ProductRepository,
) : RequestUseCase<GetProductDetail.Params, Product>() {

    data class Params(val id: String)

    override suspend fun buildRequest(params: Params) =
        repository.getProductDetail(params.id)
}
