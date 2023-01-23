package com.yusufarisoy.composepaging.data.api

import com.yusufarisoy.composepaging.data.entity.BaseResponse
import com.yusufarisoy.composepaging.data.entity.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyServiceApi {

    @GET(GET_CHARACTERS)
    suspend fun getCharacters(
        @Query("name") name: String?,
        @Query("page") page: Int?
    ): Response<BaseResponse<List<Character>>>

    @GET(GET_CHARACTER_BY_ID)
    suspend fun getCharacterById(@Path("id") id: Int): Response<Character>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        private const val GET_CHARACTERS = "character"
        private const val GET_CHARACTER_BY_ID = "character/{id}"
    }
}
