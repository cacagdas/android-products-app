package com.cacagdas.productsapp.core.util

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T : Any> : PagingSource<Long, T>() {
    override fun getRefreshKey(state: PagingState<Long, T>): Long? = null

    override suspend fun load(params: LoadParams<Long>) = runCatching {
        val page = params.key ?: 0
        getResponse(page + 1)?.let {
            LoadResult.Page(
                data = it,
                prevKey = null,
                nextKey = if (it.isNotEmpty()) page + 1 else null,
            )
        } ?: run {
            LoadResult.Error(Throwable("Something bad happened"))
        }
    }.getOrElse {
        LoadResult.Error(it)
    }

    abstract suspend fun getResponse(page: Long?): List<T>?
}
