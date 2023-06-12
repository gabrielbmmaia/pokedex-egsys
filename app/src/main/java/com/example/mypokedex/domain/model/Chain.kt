package com.example.mypokedex.domain.model

data class Chain(
    val firstEvolution: Pokemon,
    val secondEvolutions: List<Pokemon>,
    val thirdEvolutions: List<Pokemon>?
)