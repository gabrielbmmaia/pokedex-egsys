package com.example.mypokedex.data.networking.model.pokemonMove

import com.google.gson.annotations.SerializedName

data class PokemonMovesDto(
    @SerializedName("move")
    val move: com.example.mypokedex.data.networking.model.pokemonMove.MoveDto,
    @SerializedName("version_group_details")
    val moveDetails: List<com.example.mypokedex.data.networking.model.pokemonMove.MoveDetailsDto>
)