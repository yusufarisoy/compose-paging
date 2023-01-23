package com.yusufarisoy.composepaging.pagingsource

abstract class ComposePagingSourceWithQuery<in Input : ComposePagingSourceWithQuery.Params, Value : Any> {

    abstract class Params(open val page: Int, open val query: String)

    abstract suspend fun load(params: Input): ComposePagingSource.Result<Value>
}
