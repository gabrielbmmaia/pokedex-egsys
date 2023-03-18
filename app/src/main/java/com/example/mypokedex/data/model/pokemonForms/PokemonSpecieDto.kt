package com.example.mypokedex.data.model.pokemonForms

import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie
import com.google.gson.annotations.SerializedName

data class PokemonSpecieDto(
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChainDto,
    val varieties: List<VarietyDto>
) {
    fun toPokemonSpecie(): PokemonSpecie =
        PokemonSpecie(
            evolutionChain = evolutionChain.toEvolutionChain(),
            varieties = varieties.map { it.toVariety() }
        )
}
