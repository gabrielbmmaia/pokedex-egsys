package com.example.mypokedex.data.networking.model.pokemonSprite

import com.google.gson.annotations.SerializedName

data class SpritesDto(
    val versions: VersionsDto,
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("other")
    val otherArt: OtherArtDto
)
