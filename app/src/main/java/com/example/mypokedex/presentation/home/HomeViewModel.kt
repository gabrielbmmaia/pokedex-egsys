package com.example.mypokedex.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
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
        loadPokemons()
    }

    private fun loadPokemons(){
        viewModelScope.launch {
            pokemonUseCases.getPokemonList().cachedIn(this).collectLatest {
                _pokemonList.value = PokemonListState.Data(pokemons = it)
            }
        }
    }
}