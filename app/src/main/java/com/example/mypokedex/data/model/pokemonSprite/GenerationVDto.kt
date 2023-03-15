package com.example.mypokedex.data.model.pokemonSprite

import com.example.mypokedex.domain.model.pokemonSprite.GenerationV
import com.google.gson.annotations.SerializedName

data class GenerationVDto(
    @SerializedName("black-white")
    val blackWhite: BlackWhiteDto
) {
    fun toGenerationV(): GenerationV =
        GenerationV(blackWhite = blackWhite.toBlackWhite())
}