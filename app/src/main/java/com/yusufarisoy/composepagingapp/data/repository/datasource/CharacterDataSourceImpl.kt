package com.yusufarisoy.composepagingapp.data.repository.datasource

import com.yusufarisoy.composepagingapp.data.api.RickAndMortyServiceApi
import com.yusufarisoy.composepagingapp.data.entity.BaseResponse
import com.yusufarisoy.composepagingapp.data.entity.Character
import retrofit2.Response
import javax.inject.Inject

class CharactersDataSourceImpl @Inject constructor(
    private val service: RickAndMortyServiceApi
) : CharactersDataSource {

    override suspend fun getCharacters(
        characterName: String?,
        page: Int?
    ): Response<BaseResponse<List<Character>>> {
        return service.getCharacters(characterName,  page)
    }

    override suspend fun getCharacterById(id: Int): Response<Character> {
        return service.getCharacterById(id)
    }
}
