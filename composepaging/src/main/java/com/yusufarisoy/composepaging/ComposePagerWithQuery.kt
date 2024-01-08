/*
 * Created by Yusuf Arisoy on Jan 23, 2023
 * Copyright (C) 2023 Yusuf Arisoy. All rights reserved.
 * Last modified 1/8/24, 12:15 PM
 */

package com.yusufarisoy.composepaging

import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource.LoadState
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource.Result
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSourceWithQuery
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSourceWithQuery.Params

class ComposePagerWithQuery<in Input : Params, Value : Any>(
    private val pagingSource: ComposePagingSourceWithQuery<Input, Value>
) : BasePager<Value>() {

    suspend fun searchPage(params: Input) {
        setState {
            LoadState.Loading
        }
        loadPage(params)
    }

    suspend fun loadNextPage(params: Input) {
        if (!canLoadMore()) return

        setState {
            LoadState.Loading
        }
        loadPage(params)
    }

    private suspend fun loadPage(params: Input) {
        when (val result = pagingSource.load(params)) {
            is Result.Page -> {
                pushData(result.data)
                reachedLast = !result.hasNextPage
                setState {
                    LoadState.Idle
                }
            }
            is Result.Error ->
                setState {
                    LoadState.Error(result.throwable)
                }
        }
    }

    suspend fun reset(params: Input) {
        setState {
            LoadState.Initial
        }
        searchPage(params)
    }
}
