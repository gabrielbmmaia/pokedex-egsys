package com.pokemon.mypokedex.data.networking.model.pokemonEvolution

import com.pokemon.mypokedex.data.networking.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class ChainDto(
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolutionSecondDto>,
    @SerializedName("species")
    val evolutionFirst: PokemonDto
)