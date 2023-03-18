package com.example.mypokedex.data.model.pokemonSprite

import com.example.mypokedex.domain.model.pokemonSprite.Versions
import com.google.gson.annotations.SerializedName

data class VersionsDto(
    @SerializedName("generation-v")
    val generationV: GenerationVDto
) {
    fun toVersions(): Versions =
        Versions(generationV = generationV.toGenerationV())
}
