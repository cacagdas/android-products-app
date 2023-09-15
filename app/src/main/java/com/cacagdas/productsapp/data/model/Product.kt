package com.cacagdas.productsapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "product_id") val id: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "price") val price: Int?,
    @Json(name = "image") val image: String?,
    @Json(name = "description") val description: String?,
)
