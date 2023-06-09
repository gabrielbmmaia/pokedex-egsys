package com.example.mypokedex.data.model.pokemonSprite

import com.google.gson.annotations.SerializedName

data class GenerationVDto(
    @SerializedName("black-white")
    val blackWhite: BlackWhiteDto
)