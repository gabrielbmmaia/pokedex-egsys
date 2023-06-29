package com.pokemon.mypokedex.di

import com.pokemon.mypokedex.presentation.home.HomeViewModel
import com.pokemon.mypokedex.presentation.pokemonDetails.PokemonDetailsViewModel
import com.pokemon.mypokedex.presentation.pokemonForm.PokemonFormViewModel
import com.pokemon.mypokedex.presentation.splashScreen.SplashScreenViewModel
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
        factory { PokemonFormViewModel(repository = get()) }
    }
}
