package com.example.mypokedex.di

import com.example.mypokedex.presentation.home.HomeViewModel
import com.example.mypokedex.presentation.pokemonDetails.PokemonDetailsViewModel
import com.example.mypokedex.presentation.pokemonForm.PokemonFormViewModel
import com.example.mypokedex.presentation.splashScreen.SplashScreenViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    /**
     * Load de Modulos da camada de presentation
     * */
    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module = module {
        factory { HomeViewModel(pokemonUseCases = get(), pokemonRepository = get()) }
        factory { SplashScreenViewModel(pokemonRepository = get()) }
        factory { PokemonDetailsViewModel(pokemonUseCases = get(), pokemonRepository = get()) }
        factory { PokemonFormViewModel(pokemonUseCases = get()) }
    }
}
