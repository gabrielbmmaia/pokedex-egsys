package com.example.mypokedex.domain.model

import com.example.mypokedex.core.extensions.formatToPokemonNumber
import com.example.mypokedex.domain.model.pokemonMove.PokemonMoves
import com.example.mypokedex.domain.model.pokemonSprite.Sprites
import com.example.mypokedex.domain.model.pokemonType.PokemonTypes

data class PokemonDetails(
    val id: Int,
    val moves: List<PokemonMoves>,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonTypes>,
    val height: Int,
    val weight: Int
) {
    val numero = id.formatToPokemonNumber()
}
