/*
 * Created by Yusuf Arisoy on Jan 23, 2023
 * Copyright (C) 2023 Yusuf Arisoy. All rights reserved.
 * Last modified 1/8/24, 12:15 PM
 */

package com.yusufarisoy.composepaging

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.yusufarisoy.composepaging.pagingdata.ComposePagingData

inline fun <Value : Any> LazyListScope.items(
    feed: ComposePagingData<Value>,
    noinline requestNextPage: () -> Unit,
    noinline key: ((item: Value) -> Any)? = null,
    crossinline content: @Composable LazyItemScope.(item: Value) -> Unit
) {
    items(
        count = feed.items.size,
        key = if (key != null) { index -> key(feed.items[index]) } else null
    ) { index ->
        val fetchDistanceReached = remember(index, feed.items.size) {
            index == feed.items.size - feed.prefetchDistance - 1
        }
        LaunchedEffect(fetchDistanceReached) {
            if (fetchDistanceReached) requestNextPage()
        }
        content(feed.items[index])
    }
}

inline fun <Value : Any> LazyListScope.itemsIndexed(
    feed: ComposePagingData<Value>,
    noinline requestNextPage: () -> Unit,
    noinline key: ((index: Int, item: Value) -> Any)? = null,
    crossinline content: @Composable LazyItemScope.(index: Int, item: Value) -> Unit
) {
    items(
        count = feed.items.size,
        key = if (key != null) { index -> key(index, feed.items[index]) } else null
    ) { index ->
        val fetchDistanceReached = remember(index, feed.items.size) {
            index == feed.items.size - feed.prefetchDistance - 1
        }
        LaunchedEffect(fetchDistanceReached) {
            if (fetchDistanceReached) requestNextPage()
        }
        content(index, feed.items[index])
    }
}
