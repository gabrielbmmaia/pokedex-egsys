package com.example.mypokedex.data.networking.model.requests

import com.example.mypokedex.data.networking.model.pokemonEvolution.ChainDto

data class EvolutionRequest(
    val chain: ChainDto,
    val id: Int
)
