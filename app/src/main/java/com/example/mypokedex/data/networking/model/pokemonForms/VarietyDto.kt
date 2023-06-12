package com.example.mypokedex.data.networking.model.pokemonForms

import com.example.mypokedex.data.networking.model.PokemonDto
import com.google.gson.annotations.SerializedName

data class VarietyDto(
    @SerializedName("is_default")
    val isDefault: Boolean,
    val pokemon: com.example.mypokedex.data.networking.model.PokemonDto
)