package com.example.mypokedex.domain.model.pokemonEvolution

data class Chain(
    val evolvesTo: List<EvolutionSecond>,
    val evolutionFirst: Species
)