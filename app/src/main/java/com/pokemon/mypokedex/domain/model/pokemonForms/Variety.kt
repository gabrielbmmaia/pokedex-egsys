package com.pokemon.mypokedex.domain.model.pokemonForms

import com.pokemon.mypokedex.domain.model.Pokemon

data class Variety(
    val isDefault: Boolean,
    val pokemon: Pokemon
)
