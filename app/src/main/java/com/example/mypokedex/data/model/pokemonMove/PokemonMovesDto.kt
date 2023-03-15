package com.example.mypokedex.data.model.pokemonMove

import com.example.mypokedex.domain.model.pokemonMove.PokemonMoves
import com.google.gson.annotations.SerializedName

data class PokemonMovesDto(
    val move: MoveDto,
    @SerializedName("version_group_details")
    val moveDetails: List<MoveDetailsDto>
) {
    fun toPokemonMoves(): PokemonMoves =
        PokemonMoves(
            move = move.toMove(),
            moveDetails = moveDetails.map { it.toMoveDetails() }
        )
}