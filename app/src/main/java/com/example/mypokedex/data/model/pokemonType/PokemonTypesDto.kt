package com.example.mypokedex.data.model.pokemonType

import com.example.mypokedex.domain.model.pokemonType.PokemonTypes

data class PokemonTypesDto(
    val type: TypeDto
) {
    fun toPokemonTypes(): PokemonTypes =
        PokemonTypes(type = type.toType())
}
