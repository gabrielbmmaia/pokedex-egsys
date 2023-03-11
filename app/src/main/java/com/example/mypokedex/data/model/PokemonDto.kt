package com.example.mypokedex.data.model

import com.example.mypokedex.core.getPokemonId

data class PokemonDto(
    val name: String,
    val url: String,
) {
    val id = url.getPokemonId()
}