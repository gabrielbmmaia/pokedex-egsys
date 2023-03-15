package com.example.mypokedex.data.model.requests

import com.example.mypokedex.data.model.PokemonDto

data class PokemonRequest(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>
)