package com.cacagdas.productsapp.core.base.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.cacagdas.productsapp.core.util.Result

abstract class RequestUseCase<Params, Response : Any?> {

    suspend fun invoke(params: Params): Result<Response> =
        withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(buildRequest(params))
            }.getOrElse {
                Result.Error(it)
            }
        }

    abstract suspend fun buildRequest(params: Params): Response
}
