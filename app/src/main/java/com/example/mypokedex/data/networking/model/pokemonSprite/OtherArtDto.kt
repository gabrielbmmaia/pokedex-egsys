package com.example.mypokedex.data.networking.model.pokemonSprite

import com.google.gson.annotations.SerializedName

data class OtherArtDto(
    @SerializedName("official-artwork")
    val officialArtwork: com.example.mypokedex.data.networking.model.pokemonSprite.ArtWorkDto
)