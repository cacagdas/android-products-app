package com.cacagdas.productsapp.core.di

import android.content.Context
import androidx.room.Room
import com.cacagdas.productsapp.core.util.Constants.DATABASE_NAME
import com.cacagdas.productsapp.data.db.ProductDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        ProductDb::class.java,
        DATABASE_NAME,
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: ProductDb) = database.productDao()
}
