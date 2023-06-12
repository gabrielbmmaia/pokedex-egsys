package com.example.mypokedex.data.networking.model

import com.example.mypokedex.core.extensions.formatToPokemonNumber
import com.example.mypokedex.core.extensions.getPokemonId

data class PokemonDto(
    val name: String,
    val url: String
)