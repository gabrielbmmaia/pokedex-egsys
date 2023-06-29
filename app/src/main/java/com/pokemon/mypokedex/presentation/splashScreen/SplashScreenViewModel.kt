package com.pokemon.mypokedex.presentation.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    fun synchronizePokemonList() {
        viewModelScope.launch {
            pokemonRepository.synchronizePokemonList()
        }
    }
}