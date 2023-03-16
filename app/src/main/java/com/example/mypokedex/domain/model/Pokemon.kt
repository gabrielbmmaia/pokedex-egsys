package com.example.mypokedex.domain.model

import com.example.mypokedex.core.extensions.formatToPokemonNumber
import com.example.mypokedex.core.extensions.getPokemonId

data class Pokemon(
    val name: String,
    val url: String
) {
    val id = url.getPokemonId()
    val numero = formatToPokemonNumber(id.toString())
}
