/*
 * Created by Yusuf Arisoy on Jan 23, 2023
 * Copyright (C) 2023 Yusuf Arisoy. All rights reserved.
 * Last modified 1/8/24, 12:14 PM
 */

package com.yusufarisoy.composepaging.pagingdata

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class ComposePagingData<Value>(val prefetchDistance: Int = PREFETCH_DISTANCE) {

    val items: SnapshotStateList<Value> = mutableStateListOf()

    fun addPage(data: List<Value>) {
        items.addAll(data)
    }

    fun clear() {
        items.clear()
    }

    companion object {
        private const val PREFETCH_DISTANCE: Int = 0
    }
}
