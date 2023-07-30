package com.pokemon.mypokedex.data.networking.model.requests

import com.pokemon.mypokedex.data.networking.model.PokemonDto

data class PokemonRequest(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>
)