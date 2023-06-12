package com.example.mypokedex.data.networking.model.requests

import com.example.mypokedex.data.networking.model.PokemonDto

data class PokemonRequest(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<com.example.mypokedex.data.networking.model.PokemonDto>
)
