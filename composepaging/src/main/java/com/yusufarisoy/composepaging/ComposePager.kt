/*
 * Created by Yusuf Arisoy on Jan 23, 2023
 * Copyright (C) 2023 Yusuf Arisoy. All rights reserved.
 * Last modified 1/8/24, 12:15 PM
 */

package com.yusufarisoy.composepaging

import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource
import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource.LoadState

class ComposePager<Value : Any>(
    private val pagingSource: ComposePagingSource<Value>,
    private val initialPage: Int
) : BasePager<Value>() {

    private var page: Int = initialPage

    suspend fun loadFirstPage() {
        loadPage()
    }

    suspend fun loadNextPage() {
        if (!canLoadMore()) return

        page++
        setState {
            LoadState.Loading
        }
        loadPage()
    }

    private suspend fun loadPage() {
        when (val result = pagingSource.load(params = ComposePagingSource.Params(page))) {
            is ComposePagingSource.Result.Page -> {
                pushData(result.data)
                reachedLast = !result.hasNextPage
                setState {
                    LoadState.Idle
                }
            }
            is ComposePagingSource.Result.Error ->
                setState {
                    LoadState.Error(result.throwable)
                }
        }
    }

    suspend fun reset() {
        page = initialPage
        setState {
            LoadState.Initial
        }
        loadFirstPage()
    }

    fun currentPage() = page
}
