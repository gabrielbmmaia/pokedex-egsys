package com.pokemon.mypokedex.data.networking.model.pokemonMove

import com.google.gson.annotations.SerializedName

data class PokemonMovesDto(
    @SerializedName("move")
    val move: MoveDto,
    @SerializedName("version_group_details")
    val moveDetails: List<MoveDetailsDto>
)