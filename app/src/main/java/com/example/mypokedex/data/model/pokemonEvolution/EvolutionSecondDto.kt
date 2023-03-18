package com.example.mypokedex.data.model.pokemonEvolution

import com.example.mypokedex.data.model.PokemonDto
import com.example.mypokedex.domain.model.pokemonEvolution.EvolutionSecond
import com.google.gson.annotations.SerializedName

data class EvolutionSecondDto(
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolutionThirdDto>,
    @SerializedName("species")
    val evolutionSecond: PokemonDto
) {
    fun toEvolutionSecond(): EvolutionSecond =
        EvolutionSecond(
            evolvesTo = evolvesTo.map { it.toEvolutionThird() },
            evolutionSecond = evolutionSecond.toPokemon()
        )
}