package com.yusufarisoy.composepaging.data.entity

data class CharacterUiModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
    val favorite: Boolean
)
