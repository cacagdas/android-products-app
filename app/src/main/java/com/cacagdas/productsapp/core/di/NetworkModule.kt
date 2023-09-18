package com.cacagdas.productsapp.core.di

import android.app.Application
import androidx.room.Room
import com.cacagdas.productsapp.core.util.Constants.DATABASE_NAME
import com.cacagdas.productsapp.data.db.ProductDao
import com.cacagdas.productsapp.data.db.ProductDb
import com.cacagdas.productsapp.data.repo.ProductRepository
import com.cacagdas.productsapp.data.repo.ProductRepositoryImpl
import com.cacagdas.productsapp.data.source.local.ProductLocalDataSource
import com.cacagdas.productsapp.data.source.remote.ProductRemoteDataSource
import com.cacagdas.productsapp.data.source.remote.ProductService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DEFAULT_TIMEOUT = 30L
    private const val BASE_URL = "https://s3-eu-west-1.amazonaws.com/developer-application-test/"

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(service: ProductService): ProductRemoteDataSource {
        return ProductRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: ProductDao): ProductLocalDataSource {
        return ProductLocalDataSource(dao)
    }

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: ProductRemoteDataSource,
        localDataSource: ProductLocalDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(remoteDataSource, localDataSource)
    }
}
