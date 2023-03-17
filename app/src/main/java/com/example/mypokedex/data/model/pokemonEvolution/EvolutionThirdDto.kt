package com.example.mypokedex.data.model.pokemonEvolution

import com.google.gson.annotations.SerializedName

data class EvolutionThirdDto(
    @SerializedName("species")
    val evolutionThird: SpeciesDto
)
