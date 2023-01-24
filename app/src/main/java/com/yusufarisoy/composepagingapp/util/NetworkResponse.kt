package com.yusufarisoy.composepagingapp.util

sealed class NetworkResponse<out T> {

    data class Success<T>(val data: T) : NetworkResponse<T>()

    data class Error<T>(val message: String) : NetworkResponse<T>()
}

sealed class NetworkError(override val message: String) : Throwable(message) {

    data class NetworkCallError(override val message: String) : NetworkError(message)

    data class EmptyFeedError(override val message: String) : NetworkError(message)
}
