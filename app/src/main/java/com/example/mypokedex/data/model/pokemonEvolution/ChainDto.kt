package com.example.mypokedex.data.model.pokemonEvolution

import com.google.gson.annotations.SerializedName

data class ChainDto(
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolutionSecondDto>,
    @SerializedName("species")
    val evolutionFirst: SpeciesDto
)