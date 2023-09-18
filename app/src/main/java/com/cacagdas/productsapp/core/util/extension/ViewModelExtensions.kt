package com.cacagdas.productsapp.core.util.extension

import com.cacagdas.productsapp.core.util.Result

fun <T> checkResult(
    result: Result<T>,
    onSuccess: (T) -> Unit,
    onError: (String?) -> Unit
) {
    when (result) {
        is Result.Success<T> -> onSuccess(result.data)
        is Result.Error -> {
            onError(result.exception?.message ?: "Please check internet connection.")
        }
    }
}
