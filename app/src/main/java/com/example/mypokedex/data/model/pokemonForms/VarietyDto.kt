package com.example.mypokedex.data.model.pokemonForms

import com.example.mypokedex.data.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class VarietyDto(
    @SerializedName("is_default")
    val isDefault: Boolean,
    val pokemon: PokemonDto
)