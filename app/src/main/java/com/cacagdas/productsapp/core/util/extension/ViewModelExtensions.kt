package com.cacagdas.productsapp.core.util.extension

import com.cacagdas.productsapp.core.base.ProductsAppViewModel
import com.cacagdas.productsapp.core.util.Result

fun <T> ProductsAppViewModel.checkResult(result: Result<T>, onSuccess: (T) -> (Unit)) {
    when (result) {
        is Result.Success<T> -> onSuccess(result.data)
        is Result.Error -> {
            // no-op
        }
    }
}
