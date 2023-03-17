package com.example.mypokedex.data.model.pokemonEvolution

import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.google.gson.annotations.SerializedName

data class ChainDto(
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolutionSecondDto>,
    @SerializedName("species")
    val evolutionFirst: SpeciesDto
) {
    fun toChain(): Chain =
        Chain(
            evolvesTo = evolvesTo.map { it.toEvolutionSecond() },
            evolutionFirst = evolutionFirst.toSpecies()
        )
}