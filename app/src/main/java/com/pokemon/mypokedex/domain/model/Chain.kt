package com.pokemon.mypokedex.domain.model

data class Chain(
    val firstEvolution: Pokemon,
    val secondEvolutions: List<Pokemon>,
    val thirdEvolutions: List<Pokemon>?
)