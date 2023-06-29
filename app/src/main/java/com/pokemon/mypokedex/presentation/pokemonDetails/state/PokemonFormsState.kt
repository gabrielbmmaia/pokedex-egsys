package com.pokemon.mypokedex.presentation.pokemonDetails.state

import com.pokemon.mypokedex.domain.model.Pokemon

sealed class PokemonFormsState {
    data class Success(val data: List<Pokemon>) : PokemonFormsState()
    object Empty : PokemonFormsState()
}
