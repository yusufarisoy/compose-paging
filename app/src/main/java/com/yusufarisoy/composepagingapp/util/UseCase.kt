package com.yusufarisoy.composepagingapp.util

interface UseCase<Params, Result> {

    suspend fun run(params: Params): Result
}
