package com.example.mypokedex.presentation.pokemonDetails.state

sealed class PokemonState<out T> {
    class Data<T>(val data: List<T>) : PokemonState<T>()
    class Error(val message: String) : PokemonState<Nothing>()
    object Loading : PokemonState<Nothing>()
    object Empty: PokemonState<Nothing>()
}
