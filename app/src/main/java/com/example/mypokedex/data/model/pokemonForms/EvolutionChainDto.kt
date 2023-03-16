package com.example.mypokedex.data.model.pokemonForms

import com.example.mypokedex.core.extensions.getEvolutionChainId
import com.example.mypokedex.domain.model.pokemonForms.EvolutionChain

data class EvolutionChainDto(
    val url: String
) {
    val id = url.getEvolutionChainId()

    fun toEvolutionChain(): EvolutionChain =
        EvolutionChain(
            url = url,
            id = id
        )

}