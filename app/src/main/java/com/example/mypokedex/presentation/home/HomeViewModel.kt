package com.example.mypokedex.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.useCases.PokemonUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
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
                            result.message?.let { message ->
                                _pokemonList.value =
                                    PokemonListState.Error(message)
                            }
                        }
                        Resource.Loading -> {
                            _pokemonList.value =
                                PokemonListState.Loading
                        }
                        is Resource.Success -> {
                            result.data?.let { pokemonList ->
                                _pokemonList.value =
                                    PokemonListState.Data(pokemonList)
                            }
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
                            result.message?.let { message ->
                                _pokemonList.value =
                                    PokemonListState.Error(message)
                            }
                        }
                        Resource.Loading -> {
                            _pokemonList.value =
                                PokemonListState.Loading
                        }
                        is Resource.Success -> {
                            result.data?.let { pokemonList ->
                                _pokemonList.value =
                                    PokemonListState.Data(pokemonList)
                            }
                        }
                    }
                }
            }
        }
    }
}
