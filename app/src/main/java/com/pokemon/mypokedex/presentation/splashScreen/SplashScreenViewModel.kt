package com.pokemon.mypokedex.presentation.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.mypokedex.domain.repository.PokemonRepository
import com.pokemon.mypokedex.domain.useCases.PokemonUseCases
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val pokemonUseCases: PokemonUseCases
) : ViewModel() {

    fun synchronizePokemonList() {
        viewModelScope.launch {
            pokemonUseCases.synchronizePokemonList()
        }
    }
}