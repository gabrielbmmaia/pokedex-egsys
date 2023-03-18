package com.example.mypokedex.data.model.requests

import com.example.mypokedex.data.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class PokemonTipoRequest(
    @SerializedName("pokemon")
    val results: PokemonDto
)
