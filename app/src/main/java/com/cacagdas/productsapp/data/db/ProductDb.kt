package com.cacagdas.productsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cacagdas.productsapp.data.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDb : RoomDatabase() {

    abstract fun productDao(): ProductDao
}
