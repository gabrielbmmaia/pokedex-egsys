package com.example.mypokedex.presentation.pokemonDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie
import com.example.mypokedex.domain.useCases.PokemonUseCases
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonDetailsState
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PokemonDetailsViewModel(
    private val pokemonUseCases: PokemonUseCases
) : ViewModel() {

    private val _pokemonDetails = MutableStateFlow<PokemonDetailsState>(PokemonDetailsState.Loading)
    val pokemonDetails: StateFlow<PokemonDetailsState> get() = _pokemonDetails

    private val _pokemonFormas = MutableStateFlow<PokemonState<Pokemon>>(PokemonState.Loading)
    val pokemonFormas: StateFlow<PokemonState<Pokemon>> get() = _pokemonFormas

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

    fun getPokemonSpecie(pokemonId: Int) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonSpecie(pokemonId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _pokemonFormas.value =
                            PokemonState.Error(result.message!!)
                    }
                    Resource.Loading -> {
                        _pokemonFormas.value =
                            PokemonState.Loading
                    }
                    is Resource.Success -> {
                        getPokemonFormas(result.data!!)
                    }
                }
            }
        }
    }

    private fun getPokemonFormas(pokemonSpecie: PokemonSpecie) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonFormas(pokemonSpecie).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _pokemonFormas.value =
                            PokemonState.Data(result.data!!)
                    }
                    else -> {
                        _pokemonFormas.value =
                            PokemonState.Empty
                    }
                }
            }
        }
    }
}