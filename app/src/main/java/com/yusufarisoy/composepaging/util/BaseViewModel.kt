package com.yusufarisoy.composepaging.util

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BaseViewModel<S : ViewState, E : ViewEvent>(initialState: S) : ViewModel() {

    private val stateMutex = Mutex()
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: StateFlow<S>
        get() = _stateFlow.asStateFlow()
    val currentState: S
        get() =  _stateFlow.value

    private val eventMutex = Mutex()
    private val _eventChannel = Channel<E>()
    val eventFlow: Flow<E>
        get() = _eventChannel.receiveAsFlow()

    val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable -> onError(throwable) }

    protected fun setState(reducer: S.() -> S) {
        pushState {
            val newState = reducer(_stateFlow.value)
            _stateFlow.tryEmit(newState)
        }
    }

    private fun pushState(action: () -> Unit) {
        viewModelScope.launch {
            stateMutex.withLock {
                action.invoke()
            }
        }
    }

    fun pushEvent(event: E) {
        viewModelScope.launch {
            eventMutex.withLock {
                _eventChannel.send(event)
            }
        }
    }

    open fun onError(throwable: Throwable) {
        Log.e("BaseViewModel ExceptionHandler", throwable.toString())
    }
}

interface ViewState

interface ViewEvent
