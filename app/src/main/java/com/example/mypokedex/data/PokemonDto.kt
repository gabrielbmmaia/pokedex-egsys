package com.example.mypokedex.data

import com.example.mypokedex.core.getPokemonId

data class PokemonDto(
    val name: String,
    val url: String,
) {
    val id = url.getPokemonId()
}