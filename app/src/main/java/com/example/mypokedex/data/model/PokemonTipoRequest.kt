package com.example.mypokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokemonTipoRequest(
    @SerializedName("pokemon")
    val results: PokemonDto
)