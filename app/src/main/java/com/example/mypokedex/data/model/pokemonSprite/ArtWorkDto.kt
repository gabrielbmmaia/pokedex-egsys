package com.example.mypokedex.data.model.pokemonSprite

import com.example.mypokedex.domain.model.pokemonSprite.ArtWork
import com.google.gson.annotations.SerializedName

data class ArtWorkDto(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?
) {
    fun toArtWork(): ArtWork =
        ArtWork(
            frontDefault = frontDefault,
            frontShiny = frontShiny
        )
}
