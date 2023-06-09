package com.example.mypokedex.data.model

import com.example.mypokedex.data.model.pokemonMove.PokemonMovesDto
import com.example.mypokedex.data.model.pokemonSprite.SpritesDto
import com.example.mypokedex.data.model.pokemonType.PokemonTypesDto

data class PokemonDetailsDto(
    val id: Int,
    val moves: List<PokemonMovesDto>,
    val name: String,
    val sprites: SpritesDto,
    val types: List<PokemonTypesDto>,
    val height: Int,
    val weight: Int
)