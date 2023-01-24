package com.yusufarisoy.composepagingapp.util

interface Mapper<in Input, out Output> {
    fun map(input: Input): Output
}

fun <MapperClass : Mapper<Input, Output>, Input, Output> Input.mapWith(
    mapperClass: MapperClass
): Output = mapperClass.map(this)
