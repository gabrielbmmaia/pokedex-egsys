package com.example.mypokedex.data.model

import com.example.mypokedex.core.extensions.formattPokemonName
import com.example.mypokedex.data.model.pokemonMove.PokemonMovesDto
import com.example.mypokedex.data.model.pokemonSprite.SpritesDto
import com.example.mypokedex.data.model.pokemonType.PokemonTypesDto
import com.example.mypokedex.domain.model.PokemonDetails

data class PokemonDetailsDto(
    val id: Int,
    val moves: List<PokemonMovesDto>,
    val name: String,
    val sprites: SpritesDto,
    val types: List<PokemonTypesDto>,
    val height: Int,
    val weight: Int
) {
    fun toPokemonDetails(): PokemonDetails =
        PokemonDetails(
            id = id,
            moves = moves.map { it.toPokemonMoves() },
            name = name.formattPokemonName(),
            sprites = sprites.toSprites(),
            types = types.map { it.toPokemonTypes() },
            height = height,
            weight = weight
        )
}
