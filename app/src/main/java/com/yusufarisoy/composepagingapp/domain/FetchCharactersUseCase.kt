package com.yusufarisoy.composepagingapp.domain

import com.yusufarisoy.composepagingapp.data.entity.CharactersUiModel
import com.yusufarisoy.composepagingapp.data.entity.mapper.CharactersResponseToCharactersUiModelMapper
import com.yusufarisoy.composepagingapp.data.repository.CharactersRepository
import com.yusufarisoy.composepagingapp.util.NetworkError
import com.yusufarisoy.composepagingapp.util.NetworkResponse
import com.yusufarisoy.composepagingapp.util.UseCase
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
