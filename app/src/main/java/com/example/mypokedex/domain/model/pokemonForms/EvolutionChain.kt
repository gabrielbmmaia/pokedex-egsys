package com.example.mypokedex.domain.model.pokemonForms

import com.example.mypokedex.core.extensions.getEvolutionChainId

data class EvolutionChain(
    val url: String
) {
    val id = url.getEvolutionChainId()
}