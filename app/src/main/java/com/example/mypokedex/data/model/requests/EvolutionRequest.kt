package com.example.mypokedex.data.model.requests

import com.example.mypokedex.domain.model.pokemonEvolution.Chain

data class EvolutionRequest(
    val chain: Chain,
    val id: Int
)