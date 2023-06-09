package com.example.mypokedex.presentation.pokemonDetails.state

import com.example.mypokedex.domain.model.Pokemon

sealed class PokemonFormsState {
    data class Success(val data: List<Pokemon>): PokemonFormsState()
    object Empty: PokemonFormsState()
}
