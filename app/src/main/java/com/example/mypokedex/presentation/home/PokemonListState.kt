package com.example.mypokedex.presentation.home

import com.example.mypokedex.domain.model.Pokemon

sealed class PokemonListState {
    class Data(val pokemonList: List<Pokemon>) : PokemonListState()
    class Error(val message: String) : PokemonListState()
    object Loading : PokemonListState()
}
