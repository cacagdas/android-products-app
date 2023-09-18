package com.cacagdas.productsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cacagdas.productsapp.core.util.Constants.DATABASE_TABLE
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = DATABASE_TABLE)
@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "product_id") @PrimaryKey val id: String,
    @Json(name = "name") val name: String?,
    @Json(name = "price") val price: Int?,
    @Json(name = "image") val image: String?,
    @Json(name = "description") val description: String?,
)
