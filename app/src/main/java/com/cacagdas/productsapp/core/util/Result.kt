package com.cacagdas.productsapp.core.util

sealed class Result<out R> {
    data class Success<out R>(val data: R) : Result<R>()

    data class Error(
        val exception: Throwable? = null
    ) : Result<Nothing>()
}
