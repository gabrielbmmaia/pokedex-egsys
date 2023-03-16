package com.example.mypokedex.data.model

import com.example.mypokedex.core.extensions.getPokemonId
import com.example.mypokedex.domain.model.Pokemon

data class PokemonDto(
    val name: String,
    val url: String
) {
    val id = url.getPokemonId()
    fun toPokemon(): Pokemon =
        Pokemon(
            name = name.replaceFirstChar { it.uppercaseChar() },
            url = url,
            id = id
        )
}