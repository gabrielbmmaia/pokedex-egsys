package com.example.mypokedex.domain.model.pokemonForms

import com.example.mypokedex.domain.model.Pokemon

data class Variety(
    val isDefault: Boolean,
    val pokemon: Pokemon
)
