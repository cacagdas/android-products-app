package com.cacagdas.productsapp.data.source

import com.cacagdas.productsapp.data.model.Product
import retrofit2.http.*

interface ProductService {

    companion object {
        private const val PATH_CART = "cart"
    }

    @GET("$PATH_CART/list")
    suspend fun getProducts(): List<Product>

    @GET("$PATH_CART/{id}/detail")
    suspend fun getProductDetail(
        @Path("id") id: Long,
    ): Product
}
