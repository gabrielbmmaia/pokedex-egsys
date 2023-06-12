package com.example.mypokedex.data.networking.model.pokemonForms

import com.google.gson.annotations.SerializedName

data class PokemonFormsDto(
    @SerializedName("evolution_chain")
    val evolutionChain: com.example.mypokedex.data.networking.model.pokemonForms.EvolutionChainDto,
    val varieties: List<com.example.mypokedex.data.networking.model.pokemonForms.VarietyDto>
)
