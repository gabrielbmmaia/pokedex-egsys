package com.example.mypokedex.data.model.pokemonForms

import com.example.mypokedex.data.model.PokemonDto
import com.example.mypokedex.domain.model.pokemonForms.Variety
import com.google.gson.annotations.SerializedName

data class VarietyDto(
    @SerializedName("is_default")
    val isDefault: Boolean,
    val pokemon: PokemonDto
) {
    fun toVariety(): Variety =
        Variety(
            isDefault = isDefault,
            pokemon = pokemon.toPokemon()
        )
}