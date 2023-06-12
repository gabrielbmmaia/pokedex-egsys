package com.example.mypokedex.data.networking.model.pokemonSprite

import com.google.gson.annotations.SerializedName

data class VersionsDto(
    @SerializedName("generation-v")
    val generationV: GenerationVDto
)