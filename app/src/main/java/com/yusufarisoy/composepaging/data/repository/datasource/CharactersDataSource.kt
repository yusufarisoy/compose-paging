package com.yusufarisoy.composepaging.data.repository.datasource

import com.yusufarisoy.composepaging.data.entity.BaseResponse
import com.yusufarisoy.composepaging.data.entity.Character
import retrofit2.Response

interface CharactersDataSource {

    suspend fun getCharacters(
        characterName: String?,
        page: Int?
    ): Response<BaseResponse<List<Character>>>

    suspend fun getCharacterById(id: Int): Response<Character>
}
