/*
 * Created by Yusuf Arisoy on Jan 23, 2023
 * Copyright (C) 2023 Yusuf Arisoy. All rights reserved.
 * Last modified 1/8/24, 12:15 PM
 */

package com.yusufarisoy.composepaging.pagingsource

abstract class ComposePagingSourceWithQuery<in Input : ComposePagingSourceWithQuery.Params, Value : Any> {

    abstract class Params(open val page: Int, open val query: String)

    abstract suspend fun load(params: Input): ComposePagingSource.Result<Value>
}
