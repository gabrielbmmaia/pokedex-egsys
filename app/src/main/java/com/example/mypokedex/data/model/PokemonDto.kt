package com.example.mypokedex.data.model

import com.example.mypokedex.core.extensions.formattPokemonName
import com.example.mypokedex.domain.model.Pokemon

data class PokemonDto(
    val name: String,
    val url: String
) {
    fun toPokemon(): Pokemon =
        Pokemon(
            name = name.formattPokemonName(),
            url = url
        )
}
