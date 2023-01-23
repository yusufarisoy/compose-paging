package com.yusufarisoy.composepaging.domain

import com.yusufarisoy.composepaging.data.entity.CharactersUiModel
import com.yusufarisoy.composepaging.data.entity.mapper.CharactersResponseToCharactersUiModelMapper
import com.yusufarisoy.composepaging.data.repository.CharactersRepository
import com.yusufarisoy.composepaging.util.NetworkError
import com.yusufarisoy.composepaging.util.NetworkResponse
import com.yusufarisoy.composepaging.util.UseCase
import javax.inject.Inject

class FetchCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val mapper: CharactersResponseToCharactersUiModelMapper
) : UseCase<FetchCharactersUseCase.Params, CharactersUiModel> {

    override suspend fun run(params: Params): CharactersUiModel {
        return when (
            val response = charactersRepository.fetchCharacters(params.characterName, params.page)
        ) {
            is NetworkResponse.Success -> mapper.map(response.data)
            is NetworkResponse.Error -> throw NetworkError.NetworkCallError(
                message = "FetchCharactersUseCase NetworkCall Error"
            )
        }
    }

    data class Params(
        val page: Int = 0,
        val characterName: String? = null
    )
}
