package com.cacagdas.productsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cacagdas.productsapp.data.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<Product>)

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product_table WHERE id=:productId")
    suspend fun getProduct(productId: String): Product
}
