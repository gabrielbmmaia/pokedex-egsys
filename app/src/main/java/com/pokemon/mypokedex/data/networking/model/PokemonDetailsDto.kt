package com.pokemon.mypokedex.data.networking.model

import com.pokemon.mypokedex.data.networking.model.pokemonMove.PokemonMovesDto
import com.pokemon.mypokedex.data.networking.model.pokemonSprite.SpritesDto
import com.pokemon.mypokedex.data.networking.model.pokemonType.PokemonTypesDto

data class PokemonDetailsDto(
    val id: Int,
    val moves: List<PokemonMovesDto>,
    val name: String,
    val sprites: SpritesDto,
    val types: List<PokemonTypesDto>,
    val height: Int,
    val weight: Int
)