package com.example.mypokedex.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.useCases.PokemonUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonUseCases: PokemonUseCases
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<PokemonListState>(PokemonListState.Loading)
    val pokemonList: StateFlow<PokemonListState> get() = _pokemonList

    init {
        loadPokemon()
    }

    /**
     * Carrega lista de Pokemon. Em caso de PokemonType não
     * ser informado sera carregado a lista com todos Pokemon
     * e caso contrário será carregado a lista com os Pokemon do
     * tipo enviado
     * */
    fun loadPokemon(pokemonType: String? = null) {
        if (pokemonType.isNullOrBlank()) {
            viewModelScope.launch {
                pokemonUseCases.getPokemonList().collectLatest { result ->

                    when (result) {
                        is Resource.Error -> {
                            _pokemonList.value =
                                PokemonListState.Error(result.message!!)
                        }
                        Resource.Loading -> {
                            _pokemonList.value =
                                PokemonListState.Loading
                        }
                        is Resource.Success -> {
                            _pokemonList.value =
                                PokemonListState.Data(result.data!!)
                        }
                    }
                }
            }
        }
        if (!pokemonType.isNullOrBlank()) {
            viewModelScope.launch {
                pokemonUseCases.getPokemonListByType(pokemonType).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _pokemonList.value =
                                PokemonListState.Error(result.message!!)
                        }
                        Resource.Loading -> {
                            _pokemonList.value =
                                PokemonListState.Loading
                        }
                        is Resource.Success -> {
                            _pokemonList.value =
                                PokemonListState.Data(result.data!!)
                        }
                    }
                }
            }
        }
    }
}
