package com.yusufarisoy.composepagingapp.data.entity

data class CharactersUiModel(
    val count: Int,
    val characters: List<CharacterUiModel>,
    val hasNextPage: Boolean
)
