package com.cacagdas.productsapp.data.db

import androidx.room.Dao
import androidx.room.Query
import com.cacagdas.productsapp.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE id=:productId")
    fun getProduct(productId: String): Flow<Product>
}
