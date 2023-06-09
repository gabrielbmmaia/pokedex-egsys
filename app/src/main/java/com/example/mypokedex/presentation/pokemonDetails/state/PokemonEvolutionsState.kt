package com.example.mypokedex.presentation.pokemonDetails.state

import com.example.mypokedex.domain.model.Chain

sealed class PokemonEvolutionsState {
    data class Success (val data: Chain): PokemonEvolutionsState()
    object Empty: PokemonEvolutionsState()
}