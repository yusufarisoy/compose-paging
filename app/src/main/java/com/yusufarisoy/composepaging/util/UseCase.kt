package com.yusufarisoy.composepaging.util

interface UseCase<Params, Result> {

    suspend fun run(params: Params): Result
}
