package com.example.mypokedex.data.model.pokemonForms

import com.example.mypokedex.domain.model.pokemonForms.EvolutionChain

data class EvolutionChainDto(
    val url: String
) {
    fun toEvolutionChain(): EvolutionChain =
        EvolutionChain(
            url = url
        )

}