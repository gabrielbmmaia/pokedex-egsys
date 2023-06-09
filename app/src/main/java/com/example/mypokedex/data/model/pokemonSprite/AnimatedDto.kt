package com.example.mypokedex.data.model.pokemonSprite

import com.google.gson.annotations.SerializedName

data class AnimatedDto(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?
)