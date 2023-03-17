package com.example.mypokedex.data.model.pokemonEvolution

import com.example.mypokedex.domain.model.pokemonEvolution.EvolutionThird
import com.google.gson.annotations.SerializedName

data class EvolutionThirdDto(
    @SerializedName("species")
    val evolutionThird: SpeciesDto
) {
    fun toEvolutionThird(): EvolutionThird =
        EvolutionThird(
            evolutionThird = evolutionThird.toSpecies()
        )
}
