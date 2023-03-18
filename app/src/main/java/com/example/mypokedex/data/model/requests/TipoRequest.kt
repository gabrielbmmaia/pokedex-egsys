package com.example.mypokedex.data.model.requests

import com.google.gson.annotations.SerializedName

data class TipoRequest(
    val id: String,
    val name: String,
    @SerializedName("pokemon")
    val results: List<PokemonTipoRequest>
)
