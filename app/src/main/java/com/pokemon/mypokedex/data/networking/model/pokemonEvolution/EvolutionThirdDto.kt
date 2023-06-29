package com.pokemon.mypokedex.data.networking.model.pokemonEvolution

import com.pokemon.mypokedex.data.networking.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class EvolutionThirdDto(
    @SerializedName("species")
    val evolutionThird: PokemonDto
)