package com.example.mypokedex.data.model.pokemonSprite

import com.example.mypokedex.domain.model.pokemonSprite.BlackWhite
import com.google.gson.annotations.SerializedName

data class BlackWhiteDto(
    val animated: AnimatedDto
) {
    fun toBlackWhite(): BlackWhite =
        BlackWhite(animated = animated.toAnimated())
}
