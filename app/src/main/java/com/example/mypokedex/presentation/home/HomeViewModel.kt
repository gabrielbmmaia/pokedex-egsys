package com.example.mypokedex.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mypokedex.core.Resource
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

    private fun loadPokemon() {
        viewModelScope.launch {
            pokemonUseCases.getPokemonList().cachedIn(this).collectLatest {
                _pokemonList.value = PokemonListState.Data(pokemons = it)
            }
        }
    }

    fun loadPokemonByType(pokemonType: String) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonListByType(pokemonType).collectLatest { result ->
                when (result) {
                    is Resource.Error -> TODO()
                    Resource.Loading -> TODO()
                    is Resource.Success -> TODO()
                }
            }
        }
    }
}