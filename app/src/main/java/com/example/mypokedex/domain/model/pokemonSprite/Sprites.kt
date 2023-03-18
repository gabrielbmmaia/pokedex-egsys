package com.example.mypokedex.domain.model.pokemonSprite

data class Sprites(
    val versions: Versions,
    val frontDefault: String?,
    val frontShiny: String?,
    val otherArt: OtherArt
)