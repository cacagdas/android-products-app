package com.cacagdas.productsapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsResponse(
    @Json(name = "products") val products: List<Product>?,
)
