package com.example.mypokedex.data.networking.model.pokemonEvolution

import com.example.mypokedex.data.networking.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class EvolutionSecondDto(
    @SerializedName("evolves_to")
    val evolvesTo: List<com.example.mypokedex.data.networking.model.pokemonEvolution.EvolutionThirdDto>,
    @SerializedName("species")
    val evolutionSecond: com.example.mypokedex.data.networking.model.PokemonDto
)