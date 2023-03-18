package com.example.mypokedex.domain.model.pokemonEvolution

import com.example.mypokedex.domain.model.Pokemon

data class Chain(
    val evolvesTo: List<EvolutionSecond>,
    val evolutionFirst: Pokemon
)
