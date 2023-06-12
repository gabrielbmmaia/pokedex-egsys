package com.example.mypokedex.data.networking.model.requests

import com.example.mypokedex.data.networking.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class PokemonTipoRequest(
    @SerializedName("pokemon")
    val results: PokemonDto
)
