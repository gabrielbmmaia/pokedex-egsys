package com.example.mypokedex.data.model.pokemonSprite

import com.example.mypokedex.domain.model.pokemonSprite.OtherArt
import com.google.gson.annotations.SerializedName

data class OtherArtDto(
    @SerializedName("official-artwork")
    val officialArtwork: ArtWorkDto
) {
    fun toOtherArt(): OtherArt =
        OtherArt(officialArtwork = officialArtwork.toArtWork())
}
