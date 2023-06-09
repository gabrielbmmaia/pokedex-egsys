package com.example.mypokedex.data.model.pokemonEvolution

import com.example.mypokedex.data.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class EvolutionThirdDto(
    @SerializedName("species")
    val evolutionThird: PokemonDto
)