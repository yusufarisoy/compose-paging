package com.yusufarisoy.composepagingapp.data.repository

import com.yusufarisoy.composepagingapp.data.entity.BaseResponse
import com.yusufarisoy.composepagingapp.data.entity.Character
import com.yusufarisoy.composepagingapp.data.repository.datasource.CharactersDataSource
import com.yusufarisoy.composepagingapp.util.NetworkResponse
import com.yusufarisoy.composepagingapp.util.getResult
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
