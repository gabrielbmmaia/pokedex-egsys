package com.example.mypokedex.data.model.pokemonSprite

import com.example.mypokedex.domain.model.pokemonSprite.Sprites

data class SpritesDto(
    val versions: VersionsDto
) {
    fun toSprites(): Sprites =
        Sprites(
            versions = versions.toVersions()
        )
}