package com.example.mypokedex.data.model

data class PokemonRequest(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDto>
)