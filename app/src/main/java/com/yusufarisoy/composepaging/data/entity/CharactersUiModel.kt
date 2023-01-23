package com.yusufarisoy.composepaging.data.entity

data class CharactersUiModel(
    val count: Int,
    val characters: List<CharacterUiModel>,
    val hasNextPage: Boolean
)
