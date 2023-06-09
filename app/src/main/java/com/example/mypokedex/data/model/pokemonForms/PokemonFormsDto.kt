package com.example.mypokedex.data.model.pokemonForms

import com.google.gson.annotations.SerializedName

data class PokemonFormsDto(
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChainDto,
    val varieties: List<VarietyDto>
)
