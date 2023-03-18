package com.example.mypokedex.domain.useCases.pokemonUseCases

import com.example.mypokedex.domain.model.pokemonMove.PokemonMoves

/**
 * Filtra a Lista de abilidades enviado via parâmetro para apenas
 * as abilidades que o Pokemon aprende enquanto passa de level e
 * organiza a lista do menor level para o maior
 * */
class FilterToLearnableAttacksUseCase {
    operator fun invoke(pokemonMoves: List<PokemonMoves>): List<PokemonMoves> {
        val filteredList = pokemonMoves.mapNotNull { moves ->
            moves.moveDetails.lastOrNull { it.levelLearned > 0 }?.let { moveDetails ->
                PokemonMoves(moves.move, listOf(moveDetails))
            }
        }
        return filteredList.sortedBy { it.moveDetails.last().levelLearned }
    }
}
