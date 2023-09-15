package com.cacagdas.productsapp.core.base.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData

abstract class PagingUseCase<T : Any, P> {

    abstract fun invoke(params: P): LiveData<PagingData<T>>
}
