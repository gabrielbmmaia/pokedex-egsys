package com.example.mypokedex.data.model.requests

import com.example.mypokedex.data.model.pokemonEvolution.ChainDto

data class EvolutionRequest(
    val chain: ChainDto,
    val id: Int
)
