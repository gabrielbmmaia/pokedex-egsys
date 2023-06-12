package com.example.mypokedex.data.networking.model.requests

import com.example.mypokedex.data.networking.model.pokemonEvolution.ChainDto

data class EvolutionRequest(
    val chain: com.example.mypokedex.data.networking.model.pokemonEvolution.ChainDto,
    val id: Int
)
