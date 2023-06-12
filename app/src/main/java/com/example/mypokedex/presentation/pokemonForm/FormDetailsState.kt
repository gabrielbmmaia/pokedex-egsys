package com.example.mypokedex.presentation.pokemonForm

import com.example.mypokedex.domain.model.PokemonDetails

sealed class FormDetailsState {
    data class Success(val data: PokemonDetails) : FormDetailsState()
    data class Error(val message: String) : FormDetailsState()
    object Loading : FormDetailsState()
}
