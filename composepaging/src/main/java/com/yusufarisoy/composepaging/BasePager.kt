package com.yusufarisoy.composepaging

import com.yusufarisoy.composepaging.pagingsource.ComposePagingSource.LoadState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BasePager<Value : Any> {

    protected var reachedLast: Boolean = false

    private val stateMutex = Mutex()
    private val _loadState: MutableStateFlow<LoadState> = MutableStateFlow(LoadState.Initial)
    val loadState = _loadState.asStateFlow()

    private val dataMutex = Mutex()
    private val _dataChannel: Channel<List<Value>> = Channel()
    val dataFlow: Flow<List<Value>>
        get() = _dataChannel.receiveAsFlow()

    protected suspend fun setState(reducer: LoadState.() -> LoadState) {
        pushState {
            val newState = reducer(_loadState.value)
            _loadState.tryEmit(newState)
        }
    }

    private suspend fun pushState(action: () -> Unit) {
        stateMutex.withLock {
            action.invoke()
        }
    }

    protected suspend fun pushData(data: List<Value>) {
        dataMutex.withLock {
            _dataChannel.send(data)
        }
    }

    fun canLoadMore() = !reachedLast && _loadState.value == LoadState.Idle
}
