package com.example.mypokedex.presentation.pokemonDetails

import androidx.lifecycle.ViewModel
import com.example.mypokedex.domain.useCases.PokemonUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PokemonDetailsViewModel(
    private val pokemonUseCases: PokemonUseCases
) : ViewModel() {

    private val _pokemonDetails = MutableStateFlow<PokemonDetailsState>(PokemonDetailsState.Loading)
    val pokemonDetails: StateFlow<PokemonDetailsState> get() = _pokemonDetails


}