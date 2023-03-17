package com.example.mypokedex.presentation.pokemonDetails.state

import com.example.mypokedex.domain.model.PokemonDetails

sealed class PokemonDetailsState {
    class Data(val data: PokemonDetails) : PokemonDetailsState()
    class Error(val message: String) : PokemonDetailsState()
    object Loading : PokemonDetailsState()
}
