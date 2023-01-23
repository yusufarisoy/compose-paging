package com.yusufarisoy.composepaging.pagingsource

abstract class ComposePagingSource<Value : Any> {

    data class Params(val page: Int)

    sealed class LoadState {

        object Initial : LoadState()

        object Loading : LoadState()

        object Idle : LoadState()

        data class Error(val throwable: Throwable) : LoadState()
    }

    sealed class Result<Value : Any> {

        data class Error<Value : Any>(
            val throwable: Throwable
        ) : Result<Value>()

        data class Page<Value : Any>(
            val data: List<Value>,
            val hasNextPage: Boolean
        ) : Result<Value>()
    }

    abstract suspend fun load(params: Params): Result<Value>
}
