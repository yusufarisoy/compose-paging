package com.yusufarisoy.composepaging.data.entity.mapper

import com.yusufarisoy.composepaging.data.entity.BaseResponse
import com.yusufarisoy.composepaging.data.entity.Character
import com.yusufarisoy.composepaging.data.entity.CharacterUiModel
import com.yusufarisoy.composepaging.data.entity.CharactersUiModel
import com.yusufarisoy.composepaging.util.Mapper
import javax.inject.Inject

class CharactersResponseToCharactersUiModelMapper @Inject constructor() :
    Mapper<BaseResponse<List<Character>>, CharactersUiModel> {

    override fun map(input: BaseResponse<List<Character>>): CharactersUiModel {
        val characters = input.items.map { character ->
            CharacterUiModel(
                id = character.id,
                name = character.name,
                status = character.status,
                species = character.species,
                gender = character.gender,
                origin = character.origin.name,
                location = character.location.name,
                image = character.image,
                favorite = false
            )
        }

        return CharactersUiModel(
            hasNextPage = input.info.next != null,
            count = input.info.count,
            characters = characters
        )
    }
}
