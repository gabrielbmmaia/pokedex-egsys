package com.example.mypokedex.presentation.pokemonDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.useCases.PokemonUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PokemonDetailsViewModel(
    private val pokemonUseCases: PokemonUseCases
) : ViewModel() {

    private val _pokemonDetails = MutableStateFlow<PokemonDetailsState>(PokemonDetailsState.Loading)
    val pokemonDetails: StateFlow<PokemonDetailsState> get() = _pokemonDetails

    fun getPokemonDetails(pokemonOrId: String) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonDetails(pokemonOrId = pokemonOrId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _pokemonDetails.value =
                            PokemonDetailsState.Error(message = result.message!!)
                    }
                    Resource.Loading -> {
                        _pokemonDetails.value =
                            PokemonDetailsState.Loading
                    }
                    is Resource.Success -> {
                        _pokemonDetails.value =
                            PokemonDetailsState.Data(result.data!!)
                    }
                }
            }
        }
    }
}