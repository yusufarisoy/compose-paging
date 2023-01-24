package com.yusufarisoy.composepagingapp.data.repository.datasource

import com.yusufarisoy.composepagingapp.data.entity.BaseResponse
import com.yusufarisoy.composepagingapp.data.entity.Character
import retrofit2.Response

interface CharactersDataSource {

    suspend fun getCharacters(
        characterName: String?,
        page: Int?
    ): Response<BaseResponse<List<Character>>>

    suspend fun getCharacterById(id: Int): Response<Character>
}
