package com.yusufarisoy.composepaging.data.entity

import com.google.gson.annotations.SerializedName

data class BaseResponse<out T>(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val items: T
)
