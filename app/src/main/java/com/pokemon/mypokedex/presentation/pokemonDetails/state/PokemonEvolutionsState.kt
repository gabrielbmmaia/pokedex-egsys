package com.pokemon.mypokedex.presentation.pokemonDetails.state

import com.pokemon.mypokedex.domain.model.Chain

sealed class PokemonEvolutionsState {
    data class Success(val data: Chain) : PokemonEvolutionsState()
    object Empty : PokemonEvolutionsState()
}