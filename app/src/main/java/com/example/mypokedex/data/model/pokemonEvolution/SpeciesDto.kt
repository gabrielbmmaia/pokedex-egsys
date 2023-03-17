package com.example.mypokedex.data.model.pokemonEvolution

import com.example.mypokedex.domain.model.pokemonEvolution.Species

data class SpeciesDto(
    val name: String,
    val url: String
) {
    fun toSpecies(): Species =
        Species(
            name = name,
            url = url
        )
}