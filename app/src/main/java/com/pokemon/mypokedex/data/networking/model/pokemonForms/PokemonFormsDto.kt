package com.pokemon.mypokedex.data.networking.model.pokemonForms

import com.google.gson.annotations.SerializedName

data class PokemonFormsDto(
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChainDto,
    val varieties: List<VarietyDto>
)
