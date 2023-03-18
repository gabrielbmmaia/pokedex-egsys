package com.example.mypokedex.data.model.requests

import com.example.mypokedex.data.model.pokemonEvolution.ChainDto
import com.example.mypokedex.domain.model.pokemonEvolution.EvolutionRequest

data class EvolutionRequestDto(
    val chain: ChainDto,
    val id: Int
) {
    fun toEvolutionResquest(): EvolutionRequest =
        EvolutionRequest(
            chain = chain.toChain(),
            id = id
        )
}