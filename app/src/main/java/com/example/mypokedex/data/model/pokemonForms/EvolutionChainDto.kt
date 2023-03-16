package com.example.mypokedex.data.model.pokemonForms

import com.example.mypokedex.core.extensions.getEvolutionChainId

data class EvolutionChainDto(
    val url: String
) {
    val id = url.getEvolutionChainId()
}