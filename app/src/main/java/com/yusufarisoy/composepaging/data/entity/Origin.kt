package com.yusufarisoy.composepaging.data.entity

import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
