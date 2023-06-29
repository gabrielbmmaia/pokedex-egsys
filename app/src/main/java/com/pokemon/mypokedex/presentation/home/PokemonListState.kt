package com.pokemon.mypokedex.presentation.home

import com.pokemon.mypokedex.domain.model.Pokemon

sealed class PokemonListState {
    class Data(val pokemonList: List<Pokemon>) : PokemonListState()
    class Error(val message: String) : PokemonListState()
    object Loading : PokemonListState()
}
