package com.pokemon.mypokedex.presentation.pokemonForm

import com.pokemon.mypokedex.domain.model.PokemonDetails

sealed class FormDetailsState {
    data class Success(val data: PokemonDetails) : FormDetailsState()
    data class Error(val message: String) : FormDetailsState()
    object Loading : FormDetailsState()
}
