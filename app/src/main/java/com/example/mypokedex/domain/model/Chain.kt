package com.example.mypokedex.domain.model

import com.example.mypokedex.domain.model.Pokemon

data class Chain(
    val firstEvolution: Pokemon,
    val secondEvolutions: List<Pokemon>,
    val thirdEvolutions: List<Pokemon>?
)