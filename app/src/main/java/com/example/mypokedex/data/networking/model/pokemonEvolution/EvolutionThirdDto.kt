package com.example.mypokedex.data.networking.model.pokemonEvolution

import com.example.mypokedex.data.networking.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class EvolutionThirdDto(
    @SerializedName("species")
    val evolutionThird: com.example.mypokedex.data.networking.model.PokemonDto
)