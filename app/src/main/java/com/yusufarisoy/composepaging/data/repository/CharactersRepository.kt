package com.yusufarisoy.composepaging.data.repository

import com.yusufarisoy.composepaging.data.entity.BaseResponse
import com.yusufarisoy.composepaging.data.entity.Character
import com.yusufarisoy.composepaging.data.repository.datasource.CharactersDataSource
import com.yusufarisoy.composepaging.util.NetworkResponse
import com.yusufarisoy.composepaging.util.getResult
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val dataSource: CharactersDataSource
) {

    suspend fun fetchCharacters(
        characterName: String? = null,
        page: Int? = null
    ): NetworkResponse<BaseResponse<List<Character>>> {
        val response = getResult {
            dataSource.getCharacters(characterName, page)
        }

        return response
    }
}
