package com.example.mypokedex.data.model.pokemonSprite

import com.example.mypokedex.domain.model.pokemonSprite.BlackWhite
import com.google.gson.annotations.SerializedName

data class BlackWhiteDto(
    val animated: AnimatedDto,
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?
) {
    fun toBlackWhite(): BlackWhite =
        BlackWhite(
            animated = animated.toAnimated(),
            frontDefault = frontDefault,
            frontShiny = frontShiny
        )
}