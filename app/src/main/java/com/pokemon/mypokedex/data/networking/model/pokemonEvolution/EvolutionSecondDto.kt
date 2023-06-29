package com.pokemon.mypokedex.data.networking.model.pokemonEvolution

import com.pokemon.mypokedex.data.networking.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class EvolutionSecondDto(
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolutionThirdDto>,
    @SerializedName("species")
    val evolutionSecond: PokemonDto
)