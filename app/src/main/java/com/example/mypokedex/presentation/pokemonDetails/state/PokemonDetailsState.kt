package com.example.mypokedex.presentation.pokemonDetails.state

import com.example.mypokedex.domain.model.PokemonDetails

sealed class PokemonDetailsState {
    data class Success(val data: PokemonDetails) : PokemonDetailsState()
    data class Error(val message: String) : PokemonDetailsState()
    object Loading : PokemonDetailsState()
}