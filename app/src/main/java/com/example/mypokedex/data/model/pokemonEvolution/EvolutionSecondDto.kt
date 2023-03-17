package com.example.mypokedex.data.model.pokemonEvolution

import com.google.gson.annotations.SerializedName

data class EvolutionSecondDto(
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolutionThirdDto>,
    @SerializedName("species")
    val evolutionSecond: SpeciesDto
)