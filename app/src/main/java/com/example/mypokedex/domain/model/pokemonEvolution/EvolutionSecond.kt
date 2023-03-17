package com.example.mypokedex.domain.model.pokemonEvolution

data class EvolutionSecond(
    val evolvesTo: List<EvolutionThird>,
    val evolutionSecond: Species
)