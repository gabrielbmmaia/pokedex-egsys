package com.example.mypokedex.data.model.pokemonType

import com.example.mypokedex.domain.model.pokemonType.Type

data class TypeDto(
    val name: String?
) {
    fun toType(): Type =
        Type(name = name)
}
