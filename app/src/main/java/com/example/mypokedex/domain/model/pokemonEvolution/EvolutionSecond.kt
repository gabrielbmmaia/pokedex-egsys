package com.example.mypokedex.domain.model.pokemonEvolution

import com.example.mypokedex.domain.model.Pokemon

data class EvolutionSecond(
    val evolvesTo: List<EvolutionThird>,
    val evolutionSecond: Pokemon
)
