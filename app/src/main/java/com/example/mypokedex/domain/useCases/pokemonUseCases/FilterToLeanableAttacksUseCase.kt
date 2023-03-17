package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.domain.model.pokemonMove.PokemonMoves

class FilterToLeanableAttacksUseCase {
    operator fun invoke(pokemonMoves: List<PokemonMoves>): List<PokemonMoves> {
        val filteredList = pokemonMoves.mapNotNull { moves ->
            moves.moveDetails.lastOrNull { it.levelLearned > 0 }?.let { moveDetails ->
                PokemonMoves(moves.move, listOf(moveDetails))
            }
        }
        return filteredList.sortedBy { it.moveDetails.last().levelLearned }
    }
}