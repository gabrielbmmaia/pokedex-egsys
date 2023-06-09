package com.example.mypokedex.data.model.pokemonSprite

import com.google.gson.annotations.SerializedName

data class OtherArtDto(
    @SerializedName("official-artwork")
    val officialArtwork: ArtWorkDto
)