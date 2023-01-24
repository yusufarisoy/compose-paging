package com.yusufarisoy.composepagingapp.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun BaseViewModel<*, *>.secureLaunch(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    exceptionHandler: CoroutineExceptionHandler = this.exceptionHandler,
    block: suspend () -> Unit
) {
    viewModelScope.launch(dispatcher + exceptionHandler) {
        block()
    }
}

fun <V : BaseViewModel<*, E>, E> Fragment.collectEvent(
    viewModel: V,
    onEventPushed: suspend (E) -> Unit
): Job {
    return viewModel.eventFlow
        .onEach(onEventPushed)
        .collectIn(viewLifecycleOwner)
}
