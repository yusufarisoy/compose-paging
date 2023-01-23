package com.yusufarisoy.composepaging.util

import com.yusufarisoy.composepaging.data.entity.BaseResponse
import retrofit2.Response

suspend fun <T> getResult(call: suspend () -> Response<T>): NetworkResponse<T> {
    val response = call()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            return NetworkResponse.Success(body)
        }
    }

    return NetworkResponse.Error("NetworkCall error")
}
