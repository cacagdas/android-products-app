package com.cacagdas.productsapp.data.source.remote

import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.data.model.ProductsResponse
import retrofit2.http.*

interface ProductService {

    companion object {
        private const val PATH_CART = "cart"
    }

    @GET("$PATH_CART/list")
    suspend fun getProducts(): ProductsResponse

    @GET("$PATH_CART/{id}/detail")
    suspend fun getProductDetail(
        @Path("id") id: String,
    ): Product
}
