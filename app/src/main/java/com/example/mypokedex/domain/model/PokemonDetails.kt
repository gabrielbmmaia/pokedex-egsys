package com.example.mypokedex.domain.model

import com.example.mypokedex.core.extensions.formatToPokemonNumber

data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val moves: List<PokemonMoves>,
    val types: List<String?>
) {
    val number = id.formatToPokemonNumber()
}