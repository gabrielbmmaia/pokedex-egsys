package com.example.mypokedex.data.model.pokemonMove

import com.example.mypokedex.domain.model.pokemonMove.Move
import com.example.mypokedex.domain.model.pokemonMove.PokemonMoves
import com.google.gson.annotations.SerializedName

data class PokemonMovesDto(
    @SerializedName("move")
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


data class PokemonMov(
    val move: Move,
    val moveDetails: List<MoveDetails>
)

data class MoveDetails(
    val levelLearned: Int
)

data class Move(
    val name: String?
)

fun filteredList( pokemonMoves: List<PokemonMov>) {

}